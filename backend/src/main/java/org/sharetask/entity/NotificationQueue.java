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
package org.sharetask.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.sharetask.api.Constants;

/**
 * @author Michal Bocek
 * @since 1.0.0
 */
@ToString
@Entity
@Table(name = "NOTIFICATION_QUEUE")
@NoArgsConstructor
@AllArgsConstructor
@NamedQueries(value = {
		@NamedQuery(name = NotificationQueue.QUERY_NAME_FIND_EMAIL_BY_PRIORITY, query = NotificationQueue.QUERY_FIND_EMAIL_BY_PRIORITY)
	})
public class NotificationQueue implements Serializable {

	private static final long serialVersionUID = Constants.VERSION;

	public static final String QUERY_NAME_FIND_EMAIL_BY_PRIORITY = "NotificationQueue.findEmailNotificationByPriority";
	public static final String QUERY_FIND_EMAIL_BY_PRIORITY = "SELECT nq FROM NotificationQueue nq ORDER BY nq.priority.weight * nq.retry";

	public enum NotificationType {
		EMAIL;
	}

	@Id
	@Getter
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Getter
	@Enumerated(value = EnumType.STRING)
	private NotificationType type;

	@Getter
	@Column(name = "[FROM]", nullable = false, length = 255)
	private String from;

	@Getter
	@ElementCollection
    @CollectionTable(name = "NOTIFICATION_QUEUE_TO", joinColumns = {@JoinColumn(name="NOTIFICATION_QUEUE_ID")})
	@Column(name = "TO", nullable = false)
	private List<String> to = new ArrayList<String>();

	@Getter
	@Column(name = "SUBJECT", nullable = false, length = 255)
	private String subject;

	@Getter
	@Lob
	@Column(name = "MESSAGE")
	private byte[] message;

	@Getter
	@Column(name = "RETRY", nullable = false)
	private int retry;

	@Getter
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PRIORITY_CODE", nullable = false)
	private Priority priority;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_ON", nullable = false)
	private Date updatedOn;


}
