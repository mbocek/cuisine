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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.cuisine.api.Constants;

import com.google.common.collect.ImmutableList;

/**
 * @author Michal Bocek
 * @since 1.0.0
 */
@ToString
@Entity
@Table(name = "MENU")
@NoArgsConstructor
public class Menu extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = Constants.VERSION;

	@Getter @Setter
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Getter @Setter
	@Column(name = "NAME", nullable = false, length = 256)
	private String name;
	
	@OneToMany
	@JoinTable(name = "MENU_FOOD", 
		joinColumns = { @JoinColumn(name = "MENU_ID", referencedColumnName = "ID") }, 
		inverseJoinColumns = { @JoinColumn(name = "FOOD_ID", referencedColumnName = "ID") }, 
		uniqueConstraints = { @UniqueConstraint(columnNames = { "MENU_ID", "FOOD_ID" }) })
	private final List<Food> foods = new ArrayList<Food>();

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FOR_DATE", nullable = false)
	private Date forDate;

	@Getter @Setter
	@Column(name = "PRICE", precision=10, scale=2, nullable = false)
	private BigDecimal price;
	
	public Date getForDate() {
		return forDate == null ? null : (Date)forDate.clone();
	}
	
	public void setForDate(final Date forDate) {
		this.forDate = forDate == null ? null : (Date)forDate.clone();
	}
	
	public List<Food> getFoods() {
		return ImmutableList.copyOf(foods);
	}
	
	public final void addFood(final Food food) {
		foods.add(food);
	}
}
