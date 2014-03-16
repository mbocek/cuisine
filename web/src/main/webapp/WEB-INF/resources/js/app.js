'use strict';

var app = angular.module('app', [ 'ngRoute', 'app.directives', 'app.services', 'app.controllers' ]);

app.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/login', {
		templateUrl : 'templates/user/login.html',
		controller : 'UserController'
	}).otherwise({
		redirectTo : '/'
	});
} ]);

app.run(function ($rootScope) {
    $rootScope.isLoggedIn = false;
});