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
import java.util.List;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

import org.cuisine.api.OrderService;
import org.cuisine.api.dto.FoodDTO;
import org.cuisine.api.dto.MenuDTO;
import org.cuisine.api.dto.OrderDTO;
import org.cuisine.entity.Food;
import org.cuisine.entity.Menu;
import org.cuisine.entity.OrderMenu;
import org.cuisine.repository.MenuRepository;
import org.cuisine.repository.OrderMenuRepository;
import org.cuisine.utility.DTOConverter;
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
	private OrderMenuRepository orderMenuRepository;

	@Override
	public List<OrderDTO> findWithShift(final Integer shift) {
	    final Date dateOfFirstDayInWeek = getDateOfFirstDayInWeek(shift);
	    final Date dateOfLastDayInWeek = getDateOfLastDayInWeek(shift);
	    
	    // for enrichment structure
	    final List<OrderMenu> orders = orderMenuRepository.findOrderMenuBetweenDates(dateOfFirstDayInWeek, dateOfLastDayInWeek);
 	    final List<Menu> menus = menuRepository.findMenuBetweenDates(dateOfFirstDayInWeek, dateOfLastDayInWeek);
	    
	    final List<OrderDTO> ordersDTO = new ArrayList<OrderDTO>(); 
	    
	    for (final Menu menu : menus) {
			OrderDTO orderDTO = new OrderDTO(menu.getForDate());
	    	if (ordersDTO.contains(orderDTO)) {
	    		orderDTO = ordersDTO.get(ordersDTO.indexOf(orderDTO));  
	    	} else {
	    		ordersDTO.add(orderDTO);
	    	}
			
	        final MenuDTO menuDTO = new MenuDTO();
	        menuDTO.setId(menu.getId());
	        menuDTO.setName(menu.getName());
	        for (final Food food : menu.getFoods()) {	
		        menuDTO.add(DTOConverter.convert(food, FoodDTO.class));
			}
	        orderDTO.addMenu(menuDTO);
	        
	        // TODO: update orders
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
}