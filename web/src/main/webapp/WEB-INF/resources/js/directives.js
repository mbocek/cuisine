'use strict';

var directives = angular.module('app.directives', []);

directives.directive('autoFillSync', function($timeout) {
	return {
		require : 'ngModel',
		link : function(scope, elem, attrs, ngModel) {
			var origVal = elem.val();
			$timeout(function() {
				var newVal = elem.val();
				if (ngModel.$pristine && origVal !== newVal) {
					ngModel.$setViewValue(newVal);
				}
			}, 500);
		}
	};
});