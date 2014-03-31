<%--
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
--%>
<%@ page import="org.cuisine.web.utils.RequestUltil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<spring:eval expression="@applicationProps['application.version']" var="applicationVersion" />
<spring:eval expression="@applicationProps['application.revision']" var="applicationRevision" />
<% pageContext.setAttribute("locale", RequestUltil.getLocale(request)); %>
<% pageContext.setAttribute("language", RequestUltil.getLanguage(request)); %>


<!doctype html>
<html lang="en" data-ng-app="app">
	<!-- Application version: ${applicationVersion} -->
	<!-- Application revision: ${applicationRevision} -->
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta name="description" content="">
		<meta name="keywords" content="">
		<meta name="author" content="">
		<link href="<c:url value="/resources-${applicationVersion}/css/bootstrap/bootstrap.min.css"/>" rel="stylesheet" media="screen" />
		<title>cuisine</title>
	</head>
	<body data-ng-init="appBasePath = '<c:url value="/"/>'; appResourcePath = '<c:url value="/resources-${applicationVersion}" />'">

		<div class="container">
			<nav class="navbar navbar-default" role="navigation" data-ng-controller="HeaderController" data-ng-init="checkLogin()">	
				<div class="container-fluid">
					<div class="navbar-header">
						<a class="navbar-brand" href="#">{{'Cuisine' | i18n}}</a>
					</div>
					<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
						<ul class="nav navbar-nav" >
							<li data-ng-class="{ active: isActive('/')}"><a href="<c:url value="/"/>">{{'Home' | i18n}}</a></li>
	                        <li data-ng-hide="isLoggedIn" data-ng-class="{ active: isActive('/login')}">
	                        	<a href="<c:url value="#/login"/>">{{'Signin' | i18n}}</a>
	                        </li>
							<li data-ng-hide="isLoggedIn" data-ng-class="{ active: isActive('/register')}">
								<a href="<c:url value="#/register"/>">{{'Signup' | i18n}}</a>
							</li>
							<li data-ng-hide="!isLoggedIn" data-ng-controller="UserController">
								<a href="<c:url value="#/logout" />" data-ng-click="logout()">{{'Signout' | i18n}}</a>
							</li>
						</ul>
						<ul class="nav navbar-nav navbar-right">
							<li data-ng-hide="!isLoggedIn">
								<a href="<c:url value="#/user" />">{{userInfo.name}} {{userInfo.surName}}</a>
							</li>
						</ul>
					</div>
				</div>
			</nav>
			<div data-ng-view></div>
		</div>
				
		<script type="text/javascript" src="<c:url value="/resources-${applicationVersion}/js/vendor/jquery/jquery-2.1.0.min.js" />"></script>
		<script type="text/javascript" src="<c:url value="/resources-${applicationVersion}/js/vendor/bootstrap/bootstrap.min.js" />"></script>
		<script type="text/javascript" src="<c:url value="/resources-${applicationVersion}/js/vendor/angular/angular.js" />"></script>
		<script type="text/javascript" src="<c:url value="/resources-${applicationVersion}/js/vendor/angular/angular-route.js" />"></script>
		<script type="text/javascript" src="<c:url value="/resources-${applicationVersion}/js/vendor/angular/i18n/angular-locale_${locale}.js" />"></script>
		<script type="text/javascript" src="<c:url value="/resources-${applicationVersion}/js/vendor/localize/localize.js" />"></script>
		
		<script type="text/javascript" src="<c:url value="/resources-${applicationVersion}/js/app.js" />"></script>
		<script type="text/javascript" src="<c:url value="/resources-${applicationVersion}/js/controllers.js" />"></script>
		<script type="text/javascript" src="<c:url value="/resources-${applicationVersion}/js/services.js" />"></script>
		<script type="text/javascript" src="<c:url value="/resources-${applicationVersion}/js/directives.js" />"></script>
	</body>
</html>