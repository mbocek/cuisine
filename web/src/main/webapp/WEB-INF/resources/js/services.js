'use strict';

var services = angular.module('app.services', []);

services.service('user', ['$rootScope', '$http', '$log', function($rootScope, $http, $log) {
	
	this.login = function(user, success, error) {
		$log.debug("Login user (user: %o)", user);
		return $http.post($rootScope.appBasePath + "api/user/login", {username: user.username, password: user.password}).success(success).error(error);
	};
	
	this.logout = function(user, success, error) {
		$log.debug("Logging out user");
		return $http.get($rootScope.appBasePath + "api/user/logout").success(success).error(error);
	};
	
	this.isLoggedIn = function(success, error) {
		$log.debug("Cheking if user is logged in");
		return $http.get($rootScope.appBasePath + "api/user/login/status").success(success).error(error);
	};
}]);
