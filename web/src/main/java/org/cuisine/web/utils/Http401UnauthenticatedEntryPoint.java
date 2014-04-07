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

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;

/**
 * @author Michal Bocek
 * @since 1.0.0
 */
public class Http401UnauthenticatedEntryPoint implements AuthenticationEntryPoint {
    private static final Log logger = LogFactory.getLog(Http403ForbiddenEntryPoint.class);

    /**
     * Always returns a 401 error code to the client.
     */
    @Override
	public void commence(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException arg2) throws IOException,
            ServletException {
        if (logger.isDebugEnabled()) {
            logger.debug("Pre-authenticated entry point called. Rejecting access");
        }
        final HttpServletResponse httpResponse = response;
        httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthenticated request");
    }
}
