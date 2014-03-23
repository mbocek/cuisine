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
package org.cuisine.web.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Michal Bocek
 * @since 1.0.0
 */
public final class RequestUltil {

	public static String getLocale(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		String result = "";
		for(Cookie cookie : cookies) { 
			if (cookie.getName().equals("locale")) {
				result = cookie.getValue();
				break;
			}
		}
		if (result.isEmpty()) {
			result = request.getLocale().getLanguage().toLowerCase();
			if (request.getLocale().getCountry().length() > 0) {
				result = request.getLocale().getLanguage().toLowerCase() + "-" + request.getLocale().getCountry().toLowerCase();
			}
		}
		return result;
	}

	public static String getLanguage(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		String result = "";
		for(Cookie cookie : cookies) { 
			if (cookie.getName().equals("language")) {
				result = cookie.getValue();
				break;
			}
		}
		if (result.isEmpty()) {
			result = request.getLocale().getLanguage().toLowerCase();
		}
		return result;
	}
}
