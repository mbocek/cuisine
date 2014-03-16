var controllers = angular.module('app.controllers', [ ]);

controllers.controller('UserController', [ '$rootScope', '$scope', '$http', 'User', '$log', '$location',    
		function($rootScope, $scope, $http, User, $log, $location) {
			$scope.authenticate = function() {
				$log.log("Login user (username: %s) with password (password: %s)", $scope.username, $scope.password);
				User.authenticate({username: $scope.username, password: $scope.password},
					function(data, status) {
						$log.log("User auth success! data: %o, status: %o", data, status);
						$rootScope.isLoggedIn = true;
						$location.path('/');
					}, function(data, status) {
						$log.log("User auth error! data: %o, status: %o", data, status);
						$rootScope.isLoggedIn = false;
					});
			};
		} ]);

controllers.controller('HeaderController', [ '$scope', '$location',
		function($scope, $location) {
			$scope.isActive = function(viewLocation) {
				return viewLocation === $location.path();
			};
		} ]);
