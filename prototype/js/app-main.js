var main = angular.module('main', ['cookbooks', 'ngRoute', 'ngSanitize']);
 
// Services
main.service('mainService', function() {
	var statusBarText = 'This is the footer from the service. This should stay at the bottom.';
	this.setStatusBarText = function(text) {
	    statusBarText = text;
		document.getElementById("footer").innerHTML = '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' + text;	
	}
	this.getStatusBarText = function() {
	    return statusBarText;
	}
});

// Main App Controllers
main.controller('AppController', function ($scope, $route, $location, mainService) {
	$scope.$route = $route;
	$scope.statusBarText = mainService.getStatusBarText();

	$scope.goto = function (path) {
		$location.path(path);
	};
}); 
