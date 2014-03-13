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
package org.sharetask.repository.base;

import javax.inject.Inject;

import org.sharetask.entity.UserInformation;
import org.sharetask.repository.UserInformationRepository;
import org.sharetask.utility.SecurityUtil;
import org.springframework.data.domain.AuditorAware;

/**
 * @author Michal Bocek
 * @since 1.0.0
 */
public class AuditorAwareImpl implements AuditorAware<UserInformation>{

	@Inject
	private UserInformationRepository userRepository;

	@Override
	public UserInformation getCurrentAuditor() {
		final String username = SecurityUtil.getCurrentSignedInUsername();
		return userRepository.read(username);
	}

}
