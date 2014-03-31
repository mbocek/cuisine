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
package org.cuisine.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.cuisine.api.Constants;

/**
 * @author Michal Bocek
 * @since 1.0.0
 */
@ToString
@Entity
@Table(name = "FOOD_TYPE")
@NoArgsConstructor
public class FoodType extends BaseImmutableEntity implements Serializable {
	
	private static final long serialVersionUID = Constants.VERSION;

	public static enum Type {
		STARTER, SOUP, MEAL, DESSERT, DRINK, SNACK;
	}

	@Id
	@Getter @Setter
	@Enumerated(value = EnumType.STRING)
	@Column(name = "TYPE", nullable = false, length = 50)
	private Type type;

	@ElementCollection(targetClass = Role.class)
	@JoinTable(name = "FOOD_TYPE_ROLE", joinColumns = @JoinColumn(name = "TYPE"))
	@Column(name = "ROLE", nullable = false)
	@Enumerated(EnumType.STRING)
	private Collection<Role> roles = new HashSet<Role>();
	
	public Collection<Role> getRoles() {
		return Collections.unmodifiableCollection(roles);
	}
}
