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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "FOOD_ORDER")
@NoArgsConstructor
public class FoodOrder extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = Constants.VERSION;

	@Getter @Setter
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Getter	@Setter
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "MENU_ID")
	private Menu menu;

	@Getter @Setter
	@Column(name = "ADULT_PORTION_AMOUNT", nullable = false)
	private Integer adultPortionAmount;

	@Getter @Setter
	@Column(name = "CHILD_PORTION_AMOUNT", nullable = false)
	private Integer childPortionAmount;

	@Getter	@Setter
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ORDER_MAKER")
	private UserInformation orderMaker;
}
