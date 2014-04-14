var controllers = angular.module('app.controllers', [ ]);

/**
 * User controller.
 */
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
						user.get(function(data, status) {
							$rootScope.userInfo = data;
						});
						$location.path('/');
					}, function(data, status) {
						$log.log("User auth error! data: %o, status: %o", data, status);
						$scope.loginError = true;
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

/**
 * Order controller.
 */
controllers.controller('OrderController', [ '$rootScope', '$scope', '$http', 'order', '$log', '$location', 'localize',    
   		function($rootScope, $scope, $http, order, $log, $location, localize) {
   			
   			/**
   			 * Load orders
   			 */
   			$scope.load = function() {
   				$log.log("Loading orders");
   				order.load($scope.shift, 
   					function(data, status) {
   						$log.log("Load order data! data: %o, status: %o", data, status);
						$scope.orders = data;
   					}, function(data, status) {
   						$log.error("Order data load error! data: %o, status: %o", data, status);
   					});
   			};
   			
   			/**
   			 * Store orders
   			 */
   			$scope.store = function() {
   				$log.log("Loading orders");
   				order.store($scope.orders, 
   					function(data, status) {
   						$log.log("Store order data! data: %o, status: %o", data, status);
   					}, function(data, status) {
   						$log.error("Order data store error! data: %o, status: %o", data, status);
						$scope.orders = data;
   					});
   			};
   			
		} ]);

/**
 * Header controller.
 */
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
							user.get(function(data, status) {
								$rootScope.userInfo = data;
							});
							$location.path('/');
						}, function(data, status) {
							$log.log("User isn't loged in! status: %o", status);
							$rootScope.isLoggedIn = false;
						});
			};
} ]);
