
var recipes = angular.module('recipes', ['ngRoute', 'ngSanitize', 'ngResource']);
 
var urlPrefix = '';

recipes.factory('RecipesFactory', function($resource) {
    return $resource('http://localhost:8080/homeMgr/recipes', {}, {
        query: { method: 'GET', isArray: true },
        create: { method: 'POST', params: {type: 'create'} }
    })
});

recipes.factory('RecipesFactory', function($resource) {
    return $resource('http://localhost:8080/homeMgr/recipes/:id', {}, {
        show: { method: 'GET' , params: {id: '@id'} },
        update: { method: 'PUT' , params: {id: '@id'} },
        delete: { method: 'DELETE' , params: {id: '@id'} }
    })
});

// Routes
recipes.config(function($routeProvider) {
	$routeProvider.when('/recipesByLetter', {templateUrl: urlPrefix + 'recipes-by-letter.html', controller: 'RecipesController'});
	$routeProvider.otherwise({redirectTo: '/recipesByLetter'});
});

// Services
recipes.service('recipeService', function(RecipesFactory) {
	var nextRecipePk = 20;
	var allSelected = false;
	var recipeList = [];

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
                RecipesFactory.delete({ id: list[i].recipes_pk });
				recipeList.splice(i, 1)
			}
		}
	}
    this.getAll = function() {
        list = RecipesFactory.query();
        return list;
    }
    this.getItem = function(pk) {
        return RecipesFactory.show({ id: pk });
    }
	this.addItem = function(item) { item.recipes_pk = nextRecipePk++; recipeList.push(item); }
	this.removeItem = function(pk) { recipeList.splice(this.indexForPK(pk), 1) }
	this.size = function() { return recipeList.length; }
	this.isAllSelected = function() { return recipeList.allSelected; }
	this.update = function(item) { }
});

// Cookbook Controllers
recipes.controller('RecipesController', function ($scope, $rootScope, recipeService, loginService) {
    $rootScope.headerDisplay = "display: block;";
    $rootScope.bodyBackground = "";
    $rootScope.lastPage = '/recipesByLetter';
	$scope.recipeList = recipeService.getAll();
	$scope.allSelected = recipeService.isAllSelected();

/*
	$scope.edit = function (id) {
		$rootScope.goto('/adminEditCookbook/' + id);
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
cookbooks.controller('CookbookEditController', function ($scope, $rootScope, $routeParams, mainService, cookbookService) {
	$scope.cookbookToEdit = cookbookService.getItem($routeParams.id);
	$scope.backup = angular.copy($scope.cookbookToEdit);

	$scope.update = function () {
		cookbookService.update($scope.cookbookToEdit);
		mainService.setStatusBarText('Successfully updated cookbook "' + $scope.cookbookToEdit.name + '".');
		$rootScope.goto('/adminCookbooks');
	};
	
	$scope.cancel = function () {
		$scope.cookbookToEdit.name = $scope.backup.name;
		$rootScope.goto('/adminCookbooks');
	};
	
	$scope.delete = function () {
		cookbookService.removeItem($scope.cookbookToEdit.cookbooks_pk);
		mainService.setStatusBarText('Successfully deleted cookbook "' + $scope.cookbookToEdit.name + '".');
		$rootScope.goto('/adminCookbooks');
	};
});

cookbooks.controller('CookbookAddController', function ($scope, $rootScope, $routeParams, cookbookService) {
	$scope.cookbookToAdd = {'cookbooks_pk': '', 'name': ''};

	$scope.addItem = function () {
		cookbookService.addItem($scope.cookbookToAdd);
		$rootScope.goto('/adminCookbooks');
	};
	
	$scope.cancel = function () {
		$rootScope.goto('/adminCookbooks');
	};
});
*/

angular.element(document).ready(function() { 
	angular.bootstrap(document.getElementById('recipes'), ['recipes']);
});
