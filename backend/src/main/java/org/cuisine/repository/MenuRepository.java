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

import org.cuisine.entity.Menu;
import org.cuisine.repository.base.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author Michal Bocek
 * @since 1.0.0
 */
public interface MenuRepository extends BaseJpaRepository<Menu, Long> {

	@Query("SELECT m FROM Menu m WHERE m.forDate BETWEEN ?1 and ?2 ORDER BY m.forDate")
	List<Menu> findMenuBetweenDates(final Date from, final Date to);

	@Query("SELECT m FROM Menu m WHERE m.forDate >= ?1 ORDER BY m.forDate")
	List<Menu> findMenuFormDate(final Date date);
}
