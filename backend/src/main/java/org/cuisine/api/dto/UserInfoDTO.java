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
import java.util.Collections;

import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Michal Bocek
 * @since 1.0.0
 */
@ToString
public class UserInfoDTO {

	@Size(min = 6, max = 255)
	@Getter @Setter
	private String username;

	@Size(min = 1, max = 255)
	@Getter @Setter
	private String name;
	
	@Size(min = 1, max = 255)
	@Getter @Setter
	private String surName;

	@Size(min = 4, max = 20)
	@Getter @Setter
	private String mobilePhone;
	
	@Setter
	private Collection<String> roles = new ArrayList<String>();
	
	public Collection<String> getRoles() {
		return Collections.unmodifiableCollection(roles);
	}
}
