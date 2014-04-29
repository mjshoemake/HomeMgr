var main = angular.module('main', ['cookbooks', 'foodCategories', 'meals', 'recipes', 'users', 'ngRoute', 'ngSanitize']);
 
// Main Service
main.service('mainService', function() {
	var statusBarText = 'Welcome to the Shoemake Home Management application.';
	this.setStatusBarText = function(text) {
	    statusBarText = text;
		document.getElementById("footer").innerHTML = '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' + text;	
	}
	this.getStatusBarText = function() {
	    return statusBarText;
	}
});

// Login Service
main.service('loginService', function() {
    var username = '';
    var loginTime = 0;
    var timeoutDuration = 1000 * 60 * 20; // 20 minutes

    this.sessionExpired = function() {
        if (loginTime == 0) {
            username = '';
            return true;
        } else {
            var now = new Date().getTime();
            var elapsedTime = now - loginTime;
            if (elapsedTime > timeoutDuration) {
                username = '';
                return true;
            } else {
                return false;
            }
        }
    }

    this.login = function(pUsername, pPassword) {
        if ((pUsername == 'mjshoemake' && pPassword == 'qwe`123') ||
            (pUsername == 'mrshoemake' && pPassword == 'qwe`123')) {
            // Valid login.
            username = pUsername;
            loginTime = new Date().getTime();
            return true;
        } else {
            username = '';
            loginTime = 0;
            return false;
        }
    }

    this.logout = function() {
        username = '';
        loginTime = 0;
    }
});

// Main App Controllers
main.controller('AppController', function ($scope, $route, $location, mainService, loginService) {
	$scope.$route = $route;
	$scope.statusBarText = mainService.getStatusBarText();
    $scope.loginDisplay = "background-image: url(images/background.jpg); height: 100%; display: blocked;";
    $scope.appDisplay = "display: none;";

	$scope.goto = function (path) {
		$location.path(path);
	};

    $scope.logout = function () {
        loginService.logout();
        $scope.loginDisplay = "background-image: url(images/background.jpg); height: 100%; display: blocked;";
        $scope.appDisplay = "display: none;";
        mainService.setStatusBarText("You have been successfully logged out.");
    };

    $scope.login = function () {
        var success = loginService.login($scope.username, $scope.password);
        if (success) {
            $scope.loginDisplay = "background-image: url(images/background.jpg); height: 100%; display: none;";
            $scope.appDisplay = "display: blocked;";
            mainService.setStatusBarText("You have logged in successfully.  Welcome!");
        } else {
            $scope.loginDisplay = "background-image: url(images/background.jpg); height: 100%; display: blocked;";
            $scope.appDisplay = "display: none;";
            mainService.setStatusBarText("Your login credentials do not match our records.  Please try again.");
        }
    };

}); 
