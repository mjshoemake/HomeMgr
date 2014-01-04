
var cookbooks = angular.module('cookbooks', ['ngRoute', 'ngSanitize']);
 
var urlPrefix = '';

// Routes

cookbooks.config(function($routeProvider) {
	$routeProvider.when('/adminCookbooks', {templateUrl: urlPrefix + 'admin-cookbooks.html', controller: 'CookbookListController'});
	$routeProvider.when('/adminNewCookbook', {templateUrl: urlPrefix + 'admin-cookbooks-new.html', controller: 'CookbookAddController'});
	$routeProvider.when('/adminEditCookbook/:id', {templateUrl: urlPrefix + 'admin-cookbooks-edit.html', controller: 'CookbookEditController'});
	$routeProvider.when('/adminMeals', {templateUrl: urlPrefix + 'admin-meals.html', controller: 'MealListController'});
	$routeProvider.when('/adminUsers', {templateUrl: urlPrefix + 'admin-users.html', controller: 'UserListController'});
	$routeProvider.when('/adminFoodCategories', {templateUrl: urlPrefix + 'admin-food-categories.html', controller: 'FoodCategoryListController'});
	$routeProvider.otherwise({redirectTo: '/adminCookbooks'});
});

// Services
cookbooks.service('cookbookService', function() {
	var nextCookbookPk = 20;
	var allSelected = false;
	var cookbookList = [
		{'selected': false, 'cookbooks_pk': '1', 'name': '30 Minute Meals For Dummies'}, 
		{'selected': false, 'cookbooks_pk': '2', 'name': 'allrecipes.com'}, 
		{'selected': false, 'cookbooks_pk': '3', 'name': 'Betty Crocker'}, 
		{'selected': false, 'cookbooks_pk': '4', 'name': 'Chris Carmichael\'s Fitness Cookbook'}, 
		{'selected': false, 'cookbooks_pk': '5', 'name': 'Cooking Light Magazine'}, 
		{'selected': false, 'cookbooks_pk': '6', 'name': 'Gluten Free and Easy'}, 
		{'selected': false, 'cookbooks_pk': '7', 'name': 'Gran'}, 
		{'selected': false, 'cookbooks_pk': '8', 'name': 'Grandma Grace'}, 
		{'selected': false, 'cookbooks_pk': '9', 'name': 'Grandma Hilda'}, 
		{'selected': false, 'cookbooks_pk': '10', 'name': 'Kraft Shredded Cheese Packet'}, 
		{'selected': false, 'cookbooks_pk': '11', 'name': 'Lazy Day Cookin'}, 
		{'selected': false, 'cookbooks_pk': '12', 'name': 'Mamaw Shoemake'}, 
		{'selected': false, 'cookbooks_pk': '13', 'name': 'Mike\'s Test Cookbook'}, 
		{'selected': false, 'cookbooks_pk': '14', 'name': 'Personal Cookbook'}, 
		{'selected': false, 'cookbooks_pk': '15', 'name': 'Pillsbury Annual Recipes 2010'}, 
		{'selected': false, 'cookbooks_pk': '16', 'name': 'The Gluten Free Bible'}, 
		{'selected': false, 'cookbooks_pk': '17', 'name': 'The Great Potato Cookbook'}, 
		{'selected': false, 'cookbooks_pk': '18', 'name': 'www.stephanieodea.com'}, 
		{'selected': false, 'cookbooks_pk': '19', 'name': 'www.tasteofhome.com'} 
	];
	this.indexForPK = function(pk) {
		for (var i = 0; i < cookbookList.length; i++) {
			var next = cookbookList[i];
			if (next.cookbooks_pk == pk) {
				return i;
			}	
		}
		return -1;
	}
	this.selectAll = function(value) { 
		for (var i = 0; i < cookbookList.length; i++) {
			cookbookList[i].selected = value;
		}
	}
	this.removeSelected = function() { 
		for (var i = cookbookList.length - 1; i >= 0; i--) {
			if (cookbookList[i].selected) {
				cookbookList.splice(i, 1)
			}
		}
	}
	this.getAll = function() { return cookbookList; }
	this.getItem = function(pk) { return cookbookList[this.indexForPK(pk)]; }
	this.addItem = function(item) { item.cookbooks_pk = nextCookbookPk++; cookbookList.push(item); }
	this.removeItem = function(pk) { cookbookList.splice(this.indexForPK(pk), 1) }
	this.size = function() { return cookbookList.length; }
	this.isAllSelected = function() { return cookbookList.allSelected; }
	this.update = function(item) { }
});

// Cookbook Controllers
cookbooks.controller('CookbookListController', function ($scope, $location, cookbookService) {
	$scope.cookbookList = cookbookService.getAll();
	$scope.allSelected = cookbookService.isAllSelected();
	
	$scope.goto = function (path) {
		$location.path(path);
	};
	
	$scope.edit = function (id) {
		$location.path('/adminEditCookbook/' + id);
	};

	$scope.selectAll = function () {
		cookbookService.selectAll($scope.allSelected);
	};
		
	$scope.removeSelected = function () {
		cookbookService.removeSelected();
	};
		
	$scope.delete = function (id) {
		cookbookService.removeItem(id);
		$scope.cookbookList = cookbookService.getAll();
	};
		
});

cookbooks.controller('CookbookEditController', function ($scope, $routeParams, $location, mainService, cookbookService) {
	$scope.cookbookToEdit = cookbookService.getItem($routeParams.id);
	$scope.backup = angular.copy($scope.cookbookToEdit);

	$scope.update = function () {
		cookbookService.update($scope.cookbookToEdit);
		mainService.setStatusBarText('Successfully updated cookbook "' + $scope.cookbookToEdit.name + '".');
		$location.path('/adminCookbooks');
	};
	
	$scope.cancel = function () {
		$scope.cookbookToEdit.name = $scope.backup.name;
		$location.path('/adminCookbooks');
	};
	
	$scope.delete = function () {
		cookbookService.removeItem($scope.cookbookToEdit.cookbooks_pk);
		mainService.setStatusBarText('Successfully deleted cookbook "' + $scope.cookbookToEdit.name + '".');
		$location.path('/adminCookbooks');
	};
	
	$scope.goto = function (path) {
		$location.path(path);
	};	
});

cookbooks.controller('CookbookAddController', function ($scope, $routeParams, $location, cookbookService) {
	$scope.cookbookToAdd = {'cookbooks_pk': '', 'name': ''};

	$scope.addItem = function () {
		cookbookService.addItem($scope.cookbookToAdd);
		$location.path('/adminCookbooks');
	};
	
	$scope.cancel = function () {
		$location.path('/adminCookbooks');
	};
	
	$scope.goto = function (path) {
		$location.path(path);
	};	
});

angular.element(document).ready(function() { 
	angular.bootstrap(document.getElementById('cookbooks'), ['cookbooks']);
});
