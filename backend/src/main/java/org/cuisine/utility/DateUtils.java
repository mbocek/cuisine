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
package org.cuisine.utility;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Michal Bocek
 * @since 1.0.0
 */
public class DateUtils {

	/**
	 * Remove time part from date. 
	 * @param date
	 * @return
	 */
	public static Date clearTime(final Date date) {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	
//	public static boolean compareDates(final Date date1, final Date date2) {
//		final Calendar calendar1 = Calendar.getInstance();
//		calendar1.setTime(date1);
//		final Calendar calendar2 = Calendar.getInstance();
//		calendar2.setTime(date2);
//		final boolean result = false;
//		if (calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) 
//			&& calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH)
//		return result; 
//		
//	}
}
