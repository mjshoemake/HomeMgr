
var recipes = angular.module('recipes', ['ngRoute', 'ngSanitize']);
 
var urlPrefix = '';

// Routes
recipes.config(function($routeProvider) {
	$routeProvider.when('/recipesByLetter', {templateUrl: urlPrefix + 'recipes-by-letter.html', controller: 'RecipesController'});
	$routeProvider.otherwise({redirectTo: '/recipesByLetter'});
});

// Services
recipes.service('recipeService', function() {
	var nextRecipePk = 20;
	var allSelected = false;
	var recipeList = [
		{'selected': false, 'recipes_pk': '1', 'name': 'Test Recipe 1'}, 
		{'selected': false, 'recipes_pk': '2', 'name': 'Test Recipe 2'}, 
		{'selected': false, 'recipes_pk': '3', 'name': 'Test Recipe 3'}
	];
	this.indexForPK = function(pk) {
		for (var i = 0; i < recipeList.length; i++) {
			var next = recipeList[i];
			if (next.recipes_pk == pk) {
				return i;
			}	
		}
		return -1;
	}
	this.selectAll = function(value) { 
		for (var i = 0; i < recipeList.length; i++) {
			recipeList[i].selected = value;
		}
	}
	this.removeSelected = function() { 
		for (var i = recipeList.length - 1; i >= 0; i--) {
			if (recipeList[i].selected) {
				recipeList.splice(i, 1)
			}
		}
	}
	this.getAll = function() { return recipeList; }
	this.getItem = function(pk) { return recipeList[this.indexForPK(pk)]; }
	this.addItem = function(item) { item.recipes_pk = nextRecipePk++; recipeList.push(item); }
	this.removeItem = function(pk) { recipeList.splice(this.indexForPK(pk), 1) }
	this.size = function() { return recipeList.length; }
	this.isAllSelected = function() { return recipeList.allSelected; }
	this.update = function(item) { }
});

// Cookbook Controllers
cookbooks.controller('RecipesController', function ($scope, $location, recipeService) {
	$scope.recipeList = recipeService.getAll();
	$scope.allSelected = recipeService.isAllSelected();
	
	$scope.goto = function (path) {
		$location.path(path);
	};
	
/*	
	$scope.edit = function (id) {
		$location.path('/adminEditCookbook/' + id);
	};
*/	

	$scope.selectAll = function () {
		recipeService.selectAll($scope.allSelected);
	};
		
	$scope.removeSelected = function () {
		recipeService.removeSelected();
	};
		
	$scope.delete = function (id) {
		recipeService.removeItem(id);
		$scope.recipeList = recipeService.getAll();
	};
		
});

/*
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
*/

angular.element(document).ready(function() { 
	angular.bootstrap(document.getElementById('cookbooks'), ['cookbooks']);
});
