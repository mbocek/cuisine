var controllers = angular.module('app.controllers', [ ]);

controllers.controller('UserController', [ '$rootScope', '$scope', '$http', 'user', '$log', '$location',    
		function($rootScope, $scope, $http, user, $log, $location) {
			
			/**
			 * Login to application
			 */
			$scope.login = function() {
				$log.log("Login user (username: %s) with password (password: %s)", $scope.username, $scope.password);
				user.login({username: $scope.username, password: $scope.password},
					function(data, status) {
						$log.log("User auth success! data: %o, status: %o", data, status);
						$rootScope.isLoggedIn = true;
						$location.path('/');
					}, function(data, status) {
						$log.log("User auth error! data: %o, status: %o", data, status);
						$rootScope.isLoggedIn = false;
					});
			};
			
			/**
			 * Logout from application
			 */
			$scope.logout = function() {
				$log.log("Logout user");
				user.logout(
					function(data, status) {
						$log.log("User is loged out! status: %o", status);
						$rootScope.isLoggedIn = false;
						$location.path('/');	
					}, function(data, status) {
						$log.log("User isn't correctly loged out! status: %o", status);
						$rootScope.isLoggedIn = false;
					});
			};
			
		} ]);

controllers.controller('HeaderController', [ '$rootScope', '$scope', '$location', 'user', '$log', 
		function($rootScope, $scope, $location, user, $log) {
			$scope.isActive = function(viewLocation) {
				return viewLocation === $location.path();
			};

			$scope.checkLogin = function() {
				$log.log("Checking login");
				user.isLoggedIn(
						function(data, status) {
							$log.log("User is loged in! status: %o", status);
							$rootScope.isLoggedIn = true;
							$location.path('/');
						}, function(data, status) {
							$log.log("User isn't loged in! status: %o", status);
							$rootScope.isLoggedIn = false;
						});
			};
} ]);
