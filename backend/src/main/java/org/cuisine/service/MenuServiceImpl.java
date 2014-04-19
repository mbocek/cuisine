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
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

import org.cuisine.api.MenuService;
import org.cuisine.api.dto.FoodDTO;
import org.cuisine.api.dto.MenuDTO;
import org.cuisine.api.dto.MenuGroupDTO;
import org.cuisine.entity.Food;
import org.cuisine.entity.Menu;
import org.cuisine.repository.MenuRepository;
import org.cuisine.utility.DTOConverter;
import org.cuisine.utility.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Michal Bocek
 * @since 1.0.0
 */
@Slf4j
@Service("menuService")
@Transactional(readOnly = true)
public class MenuServiceImpl implements MenuService {
	
	@Inject
	private MenuRepository menuRepository;

	public List<MenuGroupDTO> getActualMenus() {
 	    final List<Menu> menus = menuRepository.findMenuFormDate(DateUtils.clearTime(new Date()));
 	    final List<MenuGroupDTO> menuGroupsDTO = new ArrayList<MenuGroupDTO>();
	    for (final Menu menu : menus) {
			MenuGroupDTO menuGroupDTO = new MenuGroupDTO(menu.getForDate());
	    	if (menuGroupsDTO.contains(menuGroupDTO)) {
	    		menuGroupDTO = menuGroupsDTO.get(menuGroupsDTO.indexOf(menuGroupDTO));  
	    	} else {
	    		menuGroupsDTO.add(menuGroupDTO);
	    	}
	    	
	        final MenuDTO menuDTO = new MenuDTO();
	    	menuDTO.setId(menu.getId());
	    	menuDTO.setName(menu.getName());
	    	menuDTO.setPrice(menu.getPrice());
	        
	        for (final Food food : menu.getFoods()) {	
		        menuDTO.add(DTOConverter.convert(food, FoodDTO.class));
			}
	        menuGroupDTO.addMenu(menuDTO);
	    }
 	    return menuGroupsDTO;
	}
}
