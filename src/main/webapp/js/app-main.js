var main = angular.module('main', ['cookbooks', 'foodCategories', 'meals', 'login', 'recipes', 'users', 'ngRoute', 'ngSanitize']);
 
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

// Routes
main.config(function($routeProvider) {
    $routeProvider.when('/login', {templateUrl: urlPrefix + 'login.html', controller: 'LoginController'});
    $routeProvider.when('/adminCookbooks', {templateUrl: urlPrefix + 'admin-cookbooks.html', controller: 'CookbookListController'});
    $routeProvider.when('/adminNewCookbook', {templateUrl: urlPrefix + 'admin-cookbooks-new.html', controller: 'CookbookAddController'});
    $routeProvider.when('/adminEditCookbook/:id', {templateUrl: urlPrefix + 'admin-cookbooks-edit.html', controller: 'CookbookEditController'});
    $routeProvider.when('/adminMeals', {templateUrl: urlPrefix + 'admin-meals.html', controller: 'MealListController'});
    $routeProvider.when('/adminUsers', {templateUrl: urlPrefix + 'admin-users.html', controller: 'UserListController'});
    $routeProvider.when('/adminFoodCategories', {templateUrl: urlPrefix + 'admin-food-categories.html', controller: 'FoodCategoryListController'});
    $routeProvider.otherwise({redirectTo: '/login'});
});

// Login Service
main.service('loginService2', function() {
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
main.controller('AppController', function ($scope, $route, $location, mainService, loginService2) {
	$scope.$route = $route;
	$scope.statusBarText = mainService.getStatusBarText();
    $scope.loginDisplay = "background-image: url(images/background.jpg); height: 100%; display: blocked;";
    $scope.appDisplay = "display: none;";

	$scope.goto = function (path) {
		$location.path(path);
	};

    $scope.logout = function () {
        loginService2.logout();
        $scope.loginDisplay = "background-image: url(images/background.jpg); height: 100%; display: blocked;";
        $scope.appDisplay = "display: none;";
        mainService.setStatusBarText("You have been successfully logged out.");
    };

    $scope.login = function () {
        var success = loginService2.login($scope.username, $scope.password);
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
