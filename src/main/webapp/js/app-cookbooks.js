
var cookbooks = angular.module('cookbooks', ['ngRoute', 'ngSanitize', 'ngResource']);
 
var urlPrefix = '';

cookbooks.factory('CookbooksFactory', function($resource) {
    return $resource('http://localhost:8080/homeMgr/cookbooks', {}, {
        query: { method: 'GET', isArray: true },
        create: { method: 'POST', params: {type: 'create'} }
    })
});

cookbooks.factory('CookbooksFactory', function($resource) {
    return $resource('http://localhost:8080/homeMgr/cookbooks/:id', {}, {
        show: { method: 'GET' , params: {id: '@id'} },
        update: { method: 'PUT' , params: {id: '@id'} },
        delete: { method: 'DELETE' , params: {id: '@id'} }
    })
});

// Routes
/*
cookbooks.config(function($routeProvider) {
	$routeProvider.when('/adminCookbooks', {templateUrl: urlPrefix + 'admin-cookbooks.html', controller: 'CookbookListController'});
	$routeProvider.when('/adminNewCookbook', {templateUrl: urlPrefix + 'admin-cookbooks-new.html', controller: 'CookbookAddController'});
	$routeProvider.when('/adminEditCookbook/:id', {templateUrl: urlPrefix + 'admin-cookbooks-edit.html', controller: 'CookbookEditController'});
	$routeProvider.when('/adminMeals', {templateUrl: urlPrefix + 'admin-meals.html', controller: 'MealListController'});
	$routeProvider.when('/adminUsers', {templateUrl: urlPrefix + 'admin-users.html', controller: 'UserListController'});
	$routeProvider.when('/adminFoodCategories', {templateUrl: urlPrefix + 'admin-food-categories.html', controller: 'FoodCategoryListController'});
	$routeProvider.otherwise({redirectTo: '/adminCookbooks'});
});
*/

// Services
cookbooks.service('cookbookService', function(CookbooksFactory) {
	var nextCookbookPk = 20;
	var allSelected = false;
	var cookbookList = [];

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
                CookbooksFactory.delete({ id: list[i].cookbooks_pk });
				cookbookList.splice(i, 1)
			}
		}
	}
    this.getAll = function() {
        list = CookbooksFactory.query();
        return list;
    }
    this.getItem = function(pk) {
        return CookbooksFactory.show({ id: pk });
    }
    this.addItem = function(item) {
        CookbooksFactory.update(item);
        //UsersFactory.create(item);
        list = CookbooksFactory.query();
        //item.user_pk = nextPk++; list.push(item);
    }
    this.removeItem = function(pk) {
        CookbooksFactory.delete({ id: pk });
        //list = UsersFactory.query();
        list.splice(this.indexForPK(pk), 1)
    }
    this.size = function() { return cookbookList.length; }
    this.isAllSelected = function() { return cookbookList.allSelected; }
    this.update = function(item) {
        CookbooksFactory.update(item);
        list = CookbooksFactory.query();
    }
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
