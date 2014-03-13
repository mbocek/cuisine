﻿<%--
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
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	// get selected locale first from cookie then from request locale
	Cookie[] cookies = request.getCookies();
	String locale = "";
	String language = "";
	for(Cookie cookie : cookies) { 
		if (cookie.getName().equals("locale")) {
			locale = cookie.getValue();
			pageContext.setAttribute("locale", locale);
			pageContext.setAttribute("language", locale);
			break;
		}
	}
	if (locale.isEmpty()) {
		pageContext.setAttribute("language", request.getLocale().getLanguage().toLowerCase());
		pageContext.setAttribute("locale", request.getLocale().getLanguage().toLowerCase());
		if (request.getLocale().getCountry().length() > 0) {
			pageContext.setAttribute("country", request.getLocale().getCountry().toLowerCase());
			pageContext.setAttribute("locale", request.getLocale().getLanguage().toLowerCase() + "-" + request.getLocale().getCountry().toLowerCase());
		}
	}
%>
<spring:eval expression="@applicationProps['application.version']" var="applicationVersion" />
<spring:eval expression="@applicationProps['application.revision']" var="applicationRevision" />
<spring:eval expression="@applicationProps['application.mail.support']" var="mailSupport" />
<spring:eval expression="@applicationProps['log.level']" var="logLevel" />
<spring:eval expression="@applicationProps['google.analytics.webapp.account']" var="googleAnalyticsAccount" />
<spring:eval expression="@applicationProps['google.analytics.webapp.track.pages']" var="googleAnalyticsTrackPages" />
<spring:eval expression="@applicationProps['google.analytics.webapp.track.events']" var="googleAnalyticsTrackEvents" />

<!doctype html>
<html lang="en" ng-app="shareTaskApp">
	<!-- Application version: ${applicationVersion} -->
	<!-- Application revision: ${applicationRevision} -->
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta name="description" content="">
		<meta name="keywords" content="">
		<meta name="author" content="">
		<title>ShareTa.sk</title>
		<link rel="shortcut icon" href="<c:url value="/resources-webapp-${applicationVersion}/favicon.ico" />" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/resources-webapp-${applicationVersion}/css/bootswatch.min.css" />">
		<link rel="stylesheet" type="text/css" href="<c:url value="/resources-webapp-${applicationVersion}/css/font-awesome.min.css" />">
		<!--[if IE 7]>
			<link rel="stylesheet" type="text/css" href="<c:url value="/resources-webapp-${applicationVersion}/css/font-awesome-ie7.min.css" />">
		<![endif]-->
		<link rel="stylesheet" type="text/css" href="<c:url value="/resources-webapp-${applicationVersion}/css/jquery.ui.datepicker.css" />">
		<link rel="stylesheet" type="text/css" href="<c:url value="/resources-webapp-${applicationVersion}/css/sharetask.css" />">
	</head>
	<body ng-cloak>
		<div ng-view></div>
		<script type="text/javascript" src="<c:url value="/resources-webapp-${applicationVersion}/scripts/vendor/jquery/jquery-1.9.1.min.js" />"></script>
		<script type="text/javascript" src="<c:url value="/resources-webapp-${applicationVersion}/scripts/vendor/jquery/jquery-ui.min.js" />"></script>
		<script type="text/javascript" src="<c:url value="/resources-webapp-${applicationVersion}/scripts/vendor/bootstrap/bootstrap.min.js" />"></script>
		<script type="text/javascript" src="<c:url value="/resources-webapp-${applicationVersion}/scripts/vendor/angular/angular.min.js" />"></script>
		<script type="text/javascript" src="<c:url value="/resources-webapp-${applicationVersion}/scripts/vendor/angular/angular-resource.min.js" />"></script>
		<script type="text/javascript" src="<c:url value="/resources-webapp-${applicationVersion}/scripts/vendor/angular/angular-ui.min.js" />"></script>
		<script type="text/javascript" src="<c:url value="/resources-webapp-${applicationVersion}/scripts/vendor/angular/angular-dragdrop.min.js" />"></script>
		<script type="text/javascript" src="<c:url value="/resources-webapp-${applicationVersion}/scripts/vendor/angular/ui-bootstrap-tpls-0.3.0.min.js" />"></script>
		<script type="text/javascript" src="<c:url value="/resources-webapp-${applicationVersion}/scripts/vendor/angular/i18n/angular-locale_${locale}.js" />"></script>
		<script type="text/javascript" src="<c:url value="/resources-webapp-${applicationVersion}/scripts/vendor/angular/angular-google-analytics.js" />"></script>
		<script type="text/javascript" src="<c:url value="/resources-webapp-${applicationVersion}/scripts/vendor/localize/localize.js" />"></script>
		<script type="text/javascript" src="<c:url value="/resources-webapp-${applicationVersion}/scripts/vendor/localize/jquery.ui.datepicker-${language}.js" />"></script>
		<script type="text/javascript" src="<c:url value="/resources-webapp-${applicationVersion}/scripts/vendor/md5-min.js" />"></script>
		<script type="text/javascript" src="<c:url value="/resources-webapp-${applicationVersion}/scripts/services/services.js" />"></script>
		<script type="text/javascript" src="<c:url value="/resources-webapp-${applicationVersion}/scripts/controllers/controllers.js" />"></script>
		<script type="text/javascript" src="<c:url value="/resources-webapp-${applicationVersion}/scripts/filters/filters.js" />"></script>
		<script type="text/javascript" src="<c:url value="/resources-webapp-${applicationVersion}/scripts/directives/directives.js" />"></script>
		<script type="text/javascript">
			// Declare app level module which depends on filters, and services
			angular.module('shareTaskApp', ['angular-google-analytics', 'shareTaskApp.filters', 'shareTaskApp.services', 'shareTaskApp.directives', 'shareTaskApp.controllers'])
				.config(['$routeProvider', 'AnalyticsProvider', function($routeProvider, AnalyticsProvider) {
					// google analytics
					AnalyticsProvider.setAccount('${googleAnalyticsAccount}');
					AnalyticsProvider.setTracking('${googleAnalyticsTrackPages}', '${googleAnalyticsTrackEvents}');
					// routing
					$routeProvider.when('/', {templateUrl: '<c:url value="/resources-webapp-${applicationVersion}/views/index.html" />'});
					$routeProvider.when('/tasks', {templateUrl: '<c:url value="/resources-webapp-${applicationVersion}/views/tasks.html" />'});
					$routeProvider.when('/workspaces', {templateUrl: '<c:url value="/resources-webapp-${applicationVersion}/views/workspaces.html" />'});
					$routeProvider.when('/user', {templateUrl: '<c:url value="/resources-webapp-${applicationVersion}/views/user.html" />'});
					$routeProvider.otherwise({redirectTo: '/'});
				}])
				.run(['$rootScope', 'Logger', function ($rootScope, Logger) {
					$rootScope.appBaseUrl = '<c:url value="/" />';
					$rootScope.appVersion = '${applicationVersion}';
					$rootScope.mailSupport = '${mailSupport}';
					$rootScope.appLocale = {language: '<c:out value="${language}" />', country: '<c:out value="${locale}" />'};
					Logger.init('${logLevel}');
				}]);
		</script>
	</body>
</html>