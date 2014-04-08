'use strict';

var app = angular.module('app', [ 'ngRoute', 'localization', 'app.directives', 'app.services', 'app.controllers' ]);

app.config([ '$routeProvider', function($routeProvider) {
	$routeProvider
	.when('/login', { templateUrl : 'templates/user/login.html', controller : 'UserController' })
	.when('/user', { templateUrl : 'templates/user/detail.html', controller : 'UserController' })
	.when('/order', { templateUrl : 'templates/food/order.html', controller : 'OrderController' })
	.otherwise({redirectTo : '/'});
} ])
.factory('authHttpResponseInterceptor',['$q','$location',function($q,$location){
    return {
        response: function(response){
            if (response.status === 401) {
                console.log("Response 401");
            }
            return response || $q.when(response);
        },
        responseError: function(rejection) {
            if (rejection.status === 401) {
                console.log("Response Error 401",rejection);
                if ($location.path() != '/login') {
                	$location.path('/login').search('returnTo', $location.path());
                }
            }
            return $q.reject(rejection);
        }
    };
}])
.config(['$httpProvider',function($httpProvider) {
    //Http Intercpetor to check auth failures for xhr requests
    $httpProvider.interceptors.push('authHttpResponseInterceptor');
}]);