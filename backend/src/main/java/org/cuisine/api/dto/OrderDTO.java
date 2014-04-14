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
package org.cuisine.api.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Michal Bocek
 * @since 1.0.0
 */
@ToString
@EqualsAndHashCode(of = { "forDate" })
@NoArgsConstructor
public class OrderDTO {

	@Getter @Setter
	private Date forDate;
	
	@Getter @Setter
	private Collection<MenuDTO> menus = new ArrayList<MenuDTO>();
	
	public OrderDTO(final Date forDate) {
		this.forDate = forDate;
	}
	
	public void addMenu(final MenuDTO menuDTO) {
		menus.add(menuDTO);
	}
}
