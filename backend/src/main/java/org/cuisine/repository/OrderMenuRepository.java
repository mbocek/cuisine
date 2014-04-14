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
package org.cuisine.repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.cuisine.entity.OrderMenu;
import org.cuisine.repository.base.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author Michal Bocek
 * @since 1.0.0
 */
public interface OrderMenuRepository extends BaseJpaRepository<OrderMenu, Long> {

	@Query("SELECT o FROM OrderMenu o WHERE o.menu.forDate BETWEEN ?1 and ?2 ORDER BY o.menu.forDate")
	List<OrderMenu> findOrderMenuBetweenDates(final Date from, final Date to);

	@Query("SELECT o FROM OrderMenu o WHERE o.orderMaker.username = ?1 and o.menu.forDate in ?2 ORDER BY o.menu.forDate")
	List<OrderMenu> findByOrderMakerAndDates(final String orderMaker, final Set<Date> forDates);
}
