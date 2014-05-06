
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
cookbooks.service('cookbookService', function(CookbooksFactory, $q) {
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
        numSelected = 0
        var itemsToDelete = ""
        for (var i = list.length - 1; i >= 0; i--) {
            if (list[i].selected) {
                if (numSelected > 0) {
                    itemsToDelete = itemsToDelete + ","
                }
                itemsToDelete = itemsToDelete + list[i].cookbooks_pk
                numSelected = numSelected + 1
            }
        }
        var deferred = $q.defer();
        CookbooksFactory.delete({ id: itemsToDelete });
        deferred.resolve();
        return deferred.promise;
	}
    this.getAll = function() {
        list = CookbooksFactory.query();
        return list;
    }
    this.getItem = function(pk) {
        return CookbooksFactory.show({ id: pk });
    }
    this.addItem = function(item) {
        var deferred = $q.defer();
        CookbooksFactory.update(item);
        deferred.resolve("done");
        return deferred.promise;
    }
    this.removeItem = function(pk) {
        var deferred = $q.defer();
        CookbooksFactory.delete({ id: pk });
        deferred.resolve("done");
        return deferred.promise;
    }
    this.size = function() { return cookbookList.length; }
    this.isAllSelected = function() { return cookbookList.allSelected; }
    this.update = function(item) {
        var deferred = $q.defer();
        CookbooksFactory.update(item);
        deferred.resolve("done");
        return deferred.promise;
    }
});

// Cookbook Controllers
cookbooks.controller('CookbookListController', function ($scope, $rootScope, mainService, cookbookService, loginService) {
    $rootScope.headerDisplay = "display: block;";
    $rootScope.bodyBackground = "";
    $rootScope.lastPage = '/adminCookbooks';
	$scope.cookbookList = cookbookService.getAll();
	$scope.allSelected = cookbookService.isAllSelected();

	$scope.edit = function (id) {
		$rootScope.goto('/adminEditCookbook/' + id);
	};

	$scope.selectAll = function () {
		cookbookService.selectAll($scope.allSelected);
	};
		
	$scope.removeSelected = function () {
		cookbookService.removeSelected().then(
            function(result) {
                $scope.cookbookList = cookbookService.getAll();
                mainService.setStatusBarText('Successfully deleted the selected cookbooks.');
            }, function(reason) {
                mainService.setStatusBarText('Failed to delete the selected cookbooks. "' + reason + '".');
                $scope.cookbookList = cookbookService.getAll();
            }
        );
	};
});

cookbooks.controller('CookbookEditController', function ($scope, $rootScope, $routeParams, mainService, cookbookService) {
	$scope.cookbookToEdit = cookbookService.getItem($routeParams.id);
	$scope.backup = angular.copy($scope.cookbookToEdit);

	$scope.update = function () {
		cookbookService.update($scope.cookbookToEdit).then(
            function(result) {
                $scope.cookbookList = cookbookService.getAll();
                mainService.setStatusBarText('Successfully updated cookbook "' + $scope.cookbookToEdit.name + '".');
                $rootScope.goto('/adminCookbooks');
            }, function(reason) {
                mainService.setStatusBarText('Failed to update cookbook. "' + reason + '".');
            }
        );
	};
	
	$scope.cancel = function () {
		$scope.cookbookToEdit.name = $scope.backup.name;
		$rootScope.goto('/adminCookbooks');
	};
	
	$scope.delete = function () {
		cookbookService.removeItem($scope.cookbookToEdit.cookbooks_pk).then(
            function(result) {
                $scope.cookbookList = cookbookService.getAll();
                mainService.setStatusBarText('Successfully deleted cookbook "' + $scope.cookbookToEdit.name + '".');
                $rootScope.goto('/adminCookbooks');
            }, function(reason) {
                mainService.setStatusBarText('Failed to delete cookbook. "' + reason + '".');
            }
        );
	};
});

cookbooks.controller('CookbookAddController', function ($scope, $rootScope, $routeParams, cookbookService) {
	$scope.cookbookToAdd = {'cookbooks_pk': '', 'name': ''};

	$scope.addItem = function () {
		cookbookService.addItem($scope.cookbookToAdd).then(
            function(result) {
                $scope.cookbookList = cookbookService.getAll();
                mainService.setStatusBarText('Successfully added cookbook "' + $scope.cookbookToAdd.name + '".');
                $rootScope.goto('/adminCookbooks');
            }, function(reason) {
                mainService.setStatusBarText('Failed to add cookbook. "' + reason + '".');
            }
        );
	};
	
	$scope.cancel = function () {
		$rootScope.goto('/adminCookbooks');
	};
});

angular.element(document).ready(function() { 
	angular.bootstrap(document.getElementById('cookbooks'), ['cookbooks']);
});
