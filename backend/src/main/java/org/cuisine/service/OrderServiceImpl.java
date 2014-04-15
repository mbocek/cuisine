/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.cuisine.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

import org.cuisine.api.OrderService;
import org.cuisine.api.dto.FoodDTO;
import org.cuisine.api.dto.OrderDTO;
import org.cuisine.api.dto.OrderGroupDTO;
import org.cuisine.entity.Food;
import org.cuisine.entity.Menu;
import org.cuisine.entity.OrderMenu;
import org.cuisine.repository.MenuRepository;
import org.cuisine.repository.OrderMenuRepository;
import org.cuisine.repository.UserInformationRepository;
import org.cuisine.utility.DTOConverter;
import org.cuisine.utility.SecurityUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Michal Bocek
 * @since 1.0.0
 */
@Slf4j
@Service("orderService")
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {
	
	@Inject
	private MenuRepository menuRepository;

	@Inject
	private UserInformationRepository userInformationRepository;

	@Inject
	private OrderMenuRepository orderMenuRepository;

	@Override
	public List<OrderGroupDTO> findWithShift(final Integer shift) {
	    final Date dateOfFirstDayInWeek = getDateOfFirstDayInWeek(shift);
	    final Date dateOfLastDayInWeek = getDateOfLastDayInWeek(shift);
	    
	    // for enrichment structure
		final List<OrderMenu> orders = orderMenuRepository.findOrderMenuBetweenDates(
				SecurityUtil.getCurrentSignedInUsername(), dateOfFirstDayInWeek, dateOfLastDayInWeek);
 	    final List<Menu> menus = menuRepository.findMenuBetweenDates(dateOfFirstDayInWeek, dateOfLastDayInWeek);
	    
	    final List<OrderGroupDTO> ordersDTO = new ArrayList<OrderGroupDTO>(); 
	    
	    for (final Menu menu : menus) {
			OrderGroupDTO orderDTO = new OrderGroupDTO(menu.getForDate());
	    	if (ordersDTO.contains(orderDTO)) {
	    		orderDTO = ordersDTO.get(ordersDTO.indexOf(orderDTO));  
	    	} else {
	    		ordersDTO.add(orderDTO);
	    	}
			
	        final OrderDTO menuDTO = new OrderDTO();
	        menuDTO.setId(menu.getId());
	        menuDTO.setName(menu.getName());
	        menuDTO.setAmountAdult(0);
	        menuDTO.setAmountChild(0);
	        menuDTO.setPrice(menu.getPrice());
	        for (final Food food : menu.getFoods()) {	
		        menuDTO.add(DTOConverter.convert(food, FoodDTO.class));
			}
	        orderDTO.addOrder(menuDTO);
		}

		// update order menu
        for (final OrderMenu order : orders) {
			for (final OrderGroupDTO orderDTO : ordersDTO) {
				for (final OrderDTO menuDTO : orderDTO.getOrders()) {
					if (orderDTO.getForDate().equals(order.getMenu().getForDate()) 
						&& menuDTO.getId().equals(order.getMenu().getId())) {
						menuDTO.setAmountAdult(order.getAdultPortionAmount());	
						menuDTO.setAmountChild(order.getChildPortionAmount());	
					}
				}
			}			
		}
			    
		return ordersDTO;
	}

	private Date getDateOfFirstDayInWeek(final Integer shift) {
		final Calendar calendar = Calendar.getInstance();
	    calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
	    calendar.add(Calendar.WEEK_OF_YEAR, shift);
	    calendar.set(Calendar.HOUR_OF_DAY, 0);
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.SECOND, 0);
	    calendar.set(Calendar.MILLISECOND, 0);
	    log.debug("Date of Fist day in week: {}", calendar.getTime());
	    return calendar.getTime();
	}

	private Date getDateOfLastDayInWeek(final Integer shift) {
		final Calendar calendar = Calendar.getInstance();
	    calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
	    calendar.add(Calendar.WEEK_OF_YEAR, shift);
	    calendar.set(Calendar.HOUR_OF_DAY, 23);
	    calendar.set(Calendar.MINUTE, 59);
	    calendar.set(Calendar.SECOND, 59);
	    calendar.set(Calendar.MILLISECOND, 999);
	    log.debug("Date of Last day in week: {}", calendar.getTime());
	    return calendar.getTime();
	}

	@Override
	@Transactional
	public void store(final List<OrderGroupDTO> orderGroups) {
		final Set<Date> dates = new HashSet<Date>();
		// all dates and cleanup on orders
		for (final OrderGroupDTO order : orderGroups) {
			final Iterator<OrderDTO> it = order.getOrders().iterator();
			while (it.hasNext()) {
				final OrderDTO menu = it.next();
				if (!((menu.getAmountAdult() != null && menu.getAmountAdult() > 0)
						|| (menu.getAmountChild() != null && menu.getAmountChild() > 0))) {
					it.remove();
				}
				dates.add(order.getForDate());
			}
		}

		log.info("Dates: {}", dates);

		final List<OrderMenu> userOrders;
		if (dates.isEmpty()) {
			userOrders = new ArrayList<OrderMenu>();
		} else {
			userOrders = orderMenuRepository.findByOrderMakerAndDates(SecurityUtil.getCurrentSignedInUsername(), dates);
		}
		
		log.info("Orders: {}", userOrders);
		// update orders
		final Iterator<OrderMenu> it = userOrders.iterator();
		while(it.hasNext()) {
			final OrderMenu orderMenu = it.next();
			for (final OrderGroupDTO order : orderGroups) {
				final Iterator<OrderDTO> itMenu = order.getOrders().iterator();
				while (itMenu.hasNext()) {
					final OrderDTO menu = itMenu.next();
					if (orderMenu.getMenu().getForDate().equals(order.getForDate())
							&& orderMenu.getMenu().getId().equals(menu.getId())) {
						orderMenu.setAdultPortionAmount(menu.getAmountAdult());
						orderMenu.setChildPortionAmount(menu.getAmountChild());
						itMenu.remove();
					}
				}
			}
		}
		
		// add new orders
		for (final OrderGroupDTO order : orderGroups) {
			for (final OrderDTO menu : order.getOrders()) {
				final OrderMenu orderMenu = new OrderMenu();
				orderMenu.setAdultPortionAmount(menu.getAmountAdult());
				orderMenu.setChildPortionAmount(menu.getAmountChild());
				orderMenu.setMenu(menuRepository.read(menu.getId()));
				orderMenu.setOrderMaker(userInformationRepository.read(SecurityUtil.getCurrentSignedInUsername()));
				userOrders.add(orderMenu);
			}
		}
		
		orderMenuRepository.save(userOrders);
	}
}
