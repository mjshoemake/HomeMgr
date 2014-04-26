
var foodCategories = angular.module('foodCategories', ['ngRoute', 'ngSanitize', 'ngResource']);
 
var urlPrefix = '';

foodCategories.factory('FoodCategoriesFactory', function($resource) {
    return $resource('http://localhost:8080/homeMgr/foodCategories', {}, {
        query: { method: 'GET', isArray: true },
        create: { method: 'POST', params: {type: 'create'} }
    })
});

foodCategories.factory('FoodCategoriesFactory', function($resource) {
    return $resource('http://localhost:8080/homeMgr/foodCategories/:id', {}, {
        show: { method: 'GET' , params: {id: '@id'} },
        update: { method: 'PUT' , params: {id: '@id'} },
        delete: { method: 'DELETE' , params: {id: '@id'} }
    })
});

// Routes
foodCategories.config(function($routeProvider) {
	$routeProvider.when('/adminFoodCategories', {templateUrl: urlPrefix + 'admin-food-categories.html', controller: 'FoodCategoryListController'});
	$routeProvider.when('/adminNewFoodCategory', {templateUrl: urlPrefix + 'admin-food-categories-new.html', controller: 'FoodCategoryAddController'});
	$routeProvider.when('/adminEditFoodCategory/:id', {templateUrl: urlPrefix + 'admin-food-categories-edit.html', controller: 'FoodCategoryEditController'});
});

// Services
foodCategories.service('foodCategoryService', function($q, FoodCategoriesFactory) {
	var nextPk = 15;
	var allSelected = false;
	var list = [];

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
        numSelected = 0
        var itemsToDelete = ""
        var namesToDelete = ""
        var allNames = ""
		for (var i = list.length - 1; i >= 0; i--) {
            if (i < list.length - 1) {
                allNames = allNames + ", "
            }
            allNames = allNames + "[" + i + "]" + list[i].name
			if (list[i].selected) {
                if (numSelected > 0) {
                    itemsToDelete = itemsToDelete + ","
                    namesToDelete = namesToDelete + ", "
                }
                itemsToDelete = itemsToDelete + list[i].meal_categories_pk
                namesToDelete = namesToDelete + "[" + i + "]" + list[i].name
                numSelected = numSelected + 1
                list.splice(i, 1)
			}
		}
        var deferred = $q.defer();
        FoodCategoriesFactory.delete({ id: itemsToDelete });
        deferred.resolve();
        return deferred.promise;
	}
    this.getAll = function() {
        list = FoodCategoriesFactory.query();
        return list;
    }
    this.getItem = function(pk) {
        return FoodCategoriesFactory.show({ id: pk });
    }
    this.addItem = function(item) {
        var deferred = $q.defer();
        FoodCategoriesFactory.update(item);
        deferred.resolve();
        return deferred.promise;
    }
    this.removeItem = function(pk) {
        var deferred = $q.defer();
        FoodCategoriesFactory.delete({ id: pk });
        deferred.resolve();
        return deferred.promise;
    }
    this.size = function() { return list.length; }
    this.isAllSelected = function() { return list.allSelected; }
    this.update = function(item) {
        var deferred = $q.defer();
        FoodCategoriesFactory.update(item);
        deferred.resolve();
        return deferred.promise;
    }
});

// Cookbook Controllers
foodCategories.controller('FoodCategoryListController', function ($scope, $location, foodCategoryService, mainService) {
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
		foodCategoryService.removeSelected().then(function(data) {
            mainService.setStatusBarText('Successfully deleted the selected food categories.');
            //$scope.foodCategoryList = foodCategoryService.getAll();
        }, function(error) {
            mainService.setStatusBarText('An error occurred trying to delete the selected food categories.');
            //$scope.foodCategoryList = foodCategoryService.getAll();
        });
	};
		
	$scope.delete = function (id) {
		foodCategoryService.removeItem(id).then(function(data) {
            $scope.foodCategoryList = foodCategoryService.getAll();
        }, function(error) {
            $scope.foodCategoryList = foodCategoryService.getAll();
        });
	};
});

foodCategories.controller('FoodCategoryEditController', function ($scope, $routeParams, $location, mainService, foodCategoryService) {
	$scope.foodCategoryToEdit = foodCategoryService.getItem($routeParams.id);
	$scope.backup = angular.copy($scope.foodCategoryToEdit);

	$scope.update = function () {
        $scope.foodCategoryList = foodCategoryService.update($scope.foodCategoryToEdit).then(function(data) {
            mainService.setStatusBarText('Successfully updated food category "' + $scope.foodCategoryToEdit.name + '".');
            $scope.foodCategoryList = foodCategoryService.getAll();
            $location.path('/adminFoodCategories');
        }, function(error) {
            mainService.setStatusBarText('An error occurred trying to update food category "' + $scope.foodCategoryToEdit.name + '".');
            $scope.foodCategoryList = foodCategoryService.getAll();
            $location.path('/adminFoodCategories');
        });
	};
	
	$scope.cancel = function () {
		$scope.foodCategoryToEdit.name = $scope.backup.name;
		$location.path('/adminFoodCategories');
	};
	
    $scope.delete = function () {
        foodCategoryService.removeItem($scope.foodCategoryToEdit.meal_categories_pk).then(function(data) {
            mainService.setStatusBarText('Successfully deleted food category "' + $scope.foodCategoryToEdit.name + '".');
            $scope.foodCategoryList = foodCategoryService.getAll();
            $location.path('/adminFoodCategories');
        }, function(error) {
            mainService.setStatusBarText('An error occurred trying to delete food category "' + $scope.foodCategoryToEdit.name + '".');
            $scope.foodCategoryList = foodCategoryService.getAll();
            $location.path('/adminFoodCategories');
        });
    };
	$scope.goto = function (path) {
		$location.path(path);
	};	
});

foodCategories.controller('FoodCategoryAddController', function ($scope, $routeParams, $location, mainService, foodCategoryService) {
	$scope.foodCategoryToAdd = {'meal_categories_pk': '', 'name': ''};

	$scope.addItem = function () {
        foodCategoryService.addItem($scope.foodCategoryToAdd).then(function(data) {
            mainService.setStatusBarText('Successfully added food category "' + $scope.foodCategoryToAdd.name + '".');
            $scope.foodCategoryList = foodCategoryService.getAll();
            $location.path('/adminFoodCategories');
        }, function(error) {
            mainService.setStatusBarText('An error occurred trying to add food category "' + $scope.foodCategoryToAdd.name + '".');
            $scope.foodCategoryList = foodCategoryService.getAll();
            $location.path('/adminFoodCategories');
        });
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
