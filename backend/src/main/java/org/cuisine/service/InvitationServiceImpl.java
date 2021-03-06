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
package org.cuisine.service;

import javax.inject.Inject;
import javax.persistence.NoResultException;

import org.cuisine.api.InvitationService;
import org.cuisine.api.MailService;
import org.cuisine.api.dto.InvitationDTO;
import org.cuisine.entity.Invitation;
import org.cuisine.entity.Invitation.InvitationType;
import org.cuisine.repository.InvitationRepository;
import org.cuisine.utility.DTOConverter;
import org.cuisine.utility.HashCodeUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Michal Bocek
 * @since 1.0.0
 */
@Service
@Transactional(readOnly = true)
public class InvitationServiceImpl implements InvitationService {

	@Inject
	private MailService mailService;

	@Inject
	private InvitationRepository invitationRepository;

	@Override
	@Transactional
	public void inviteRegisteredUser(final String username) {
		final Invitation invitation = new Invitation();
		invitation.setType(InvitationType.USER_REGISTRATION);
		invitation.setUsername(username);
		invitation.setCreatedBy(username);
		invitation.setInvitationCode(HashCodeUtil.getShortHashCode(System.currentTimeMillis() + username));
		final Invitation storedInitation = invitationRepository.save(invitation);
		//send invitation notification
		final InvitationDTO invitationDTO = DTOConverter.convert(storedInitation, InvitationDTO.class);
		mailService.sendInvitation(invitationDTO);
	}
	
	@Override
	@Transactional
	public InvitationDTO confirmInvitation(final String code) {
		Invitation invitation = null;
		try {
			invitation = invitationRepository.findByInvitationCode(code);
			invitationRepository.delete(invitation);
		} catch (final NoResultException e) {
			throw new InvitationDoesntExistsException(code);
		}
		return DTOConverter.convert(invitation, InvitationDTO.class);
	}
}
