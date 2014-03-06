
var foodCategories = angular.module('foodCategories', ['ngRoute', 'ngSanitize']);
 
var urlPrefix = '';

// Routes

foodCategories.config(function($routeProvider) {
	$routeProvider.when('/adminFoodCategories', {templateUrl: urlPrefix + 'admin-food-categories.html', controller: 'FoodCategoryListController'});
	$routeProvider.when('/adminNewFoodCategory', {templateUrl: urlPrefix + 'admin-food-categories-new.html', controller: 'FoodCategoryAddController'});
	$routeProvider.when('/adminEditFoodCategory/:id', {templateUrl: urlPrefix + 'admin-food-categories-edit.html', controller: 'FoodCategoryEditController'});
});

// Services
foodCategories.service('foodCategoryService', function() {
	var nextPk = 15;
	var allSelected = false;
	var list = [
		{'selected': false, 'meal_categories_pk': '1', 'name': 'Beans'}, 
		{'selected': false, 'meal_categories_pk': '2', 'name': 'Beverage'}, 
		{'selected': false, 'meal_categories_pk': '3', 'name': 'Chicken'}, 
		{'selected': false, 'meal_categories_pk': '4', 'name': 'Dessert'}, 
		{'selected': false, 'meal_categories_pk': '5', 'name': 'Egg'}, 
		{'selected': false, 'meal_categories_pk': '6', 'name': 'Fish'}, 
		{'selected': false, 'meal_categories_pk': '7', 'name': 'Fruit'}, 
		{'selected': false, 'meal_categories_pk': '8', 'name': 'Ground Meat'}, 
		{'selected': false, 'meal_categories_pk': '9', 'name': 'Pasta'}, 
		{'selected': false, 'meal_categories_pk': '10', 'name': 'Potatoes'}, 
		{'selected': false, 'meal_categories_pk': '11', 'name': 'Rice'}, 
		{'selected': false, 'meal_categories_pk': '12', 'name': 'Snacks'}, 
		{'selected': false, 'meal_categories_pk': '13', 'name': 'Soup/Chili'}, 
		{'selected': false, 'meal_categories_pk': '14', 'name': 'Vegetable'} 
	];
	this.indexForPK = function(pk) {
		for (var i = 0; i < list.length; i++) {
			var next = list[i];
			if (next.meal_categories_pk == pk) {
				return i;
			}	
		}
		return -1;
	}
	this.selectAll = function(value) { 
		for (var i = 0; i < list.length; i++) {
			list[i].selected = value;
		}
	}
	this.removeSelected = function() { 
		for (var i = list.length - 1; i >= 0; i--) {
			if (list[i].selected) {
				list.splice(i, 1)
			}
		}
	}
	this.getAll = function() { return list; }
	this.getItem = function(pk) { return list[this.indexForPK(pk)]; }
	this.addItem = function(item) { item.meal_categories_pk = nextPk++; list.push(item); }
	this.removeItem = function(pk) { list.splice(this.indexForPK(pk), 1) }
	this.size = function() { return list.length; }
	this.isAllSelected = function() { return list.allSelected; }
	this.update = function(item) { }
});

// Cookbook Controllers
foodCategories.controller('FoodCategoryListController', function ($scope, $location, foodCategoryService) {
	$scope.foodCategoryList = foodCategoryService.getAll();
	$scope.allSelected = foodCategoryService.isAllSelected();
	
	$scope.goto = function (path) {
		$location.path(path);
	};
	
	$scope.edit = function (id) {
		$location.path('/adminEditFoodCategory/' + id);
	};

	$scope.selectAll = function () {
		foodCategoryService.selectAll($scope.allSelected);
	};
		
	$scope.removeSelected = function () {
		foodCategoryService.removeSelected();
	};
		
	$scope.delete = function (id) {
		foodCategoryService.removeItem(id);
		$scope.foodCategoryList = foodCategoryService.getAll();
	};
		
});

foodCategories.controller('FoodCategoryEditController', function ($scope, $routeParams, $location, mainService, foodCategoryService) {
	$scope.foodCategoryToEdit = foodCategoryService.getItem($routeParams.id);
	$scope.backup = angular.copy($scope.foodCategoryToEdit);

	$scope.update = function () {
		foodCategoryService.update($scope.foodCategoryToEdit);
		mainService.setStatusBarText('Successfully updated food category "' + $scope.foodCategoryToEdit.name + '".');
		$location.path('/adminFoodCategories');
	};
	
	$scope.cancel = function () {
		$scope.foodCategoryToEdit.name = $scope.backup.name;
		$location.path('/adminFoodCategories');
	};
	
	$scope.delete = function () {
		foodCategoryService.removeItem($scope.foodCategoryToEdit.meal_categories_pk);
		mainService.setStatusBarText('Successfully deleted food category "' + $scope.foodCategoryToEdit.name + '".');
		$location.path('/adminFoodCategories');
	};
	
	$scope.goto = function (path) {
		$location.path(path);
	};	
});

foodCategories.controller('FoodCategoryAddController', function ($scope, $routeParams, $location, foodCategoryService) {
	$scope.foodCategoryToAdd = {'meal_categories_pk': '', 'name': ''};

	$scope.addItem = function () {
		foodCategoryService.addItem($scope.foodCategoryToAdd);
		$location.path('/adminFoodCategories');
	};
	
	$scope.cancel = function () {
		$location.path('/adminFoodCategories');
	};
	
	$scope.goto = function (path) {
		$location.path(path);
	};	
});

angular.element(document).ready(function() { 
	angular.bootstrap(document.getElementById('foodCategories'), ['foodCategories']);
});
