
var meals = angular.module('meals', ['ngRoute', 'ngSanitize', 'ngResource']);
 
var urlPrefix = '';

meals.factory('MealsFactory', function($resource) {
    return $resource('http://localhost:8080/homeMgr/meals', {}, {
        query: { method: 'GET', isArray: true },
        create: { method: 'POST', params: {type: 'create'} }
    })
});

meals.factory('MealsFactory', function($resource) {
    return $resource('http://localhost:8080/homeMgr/meals/:id', {}, {
        show: { method: 'GET' , params: {id: '@id'} },
        update: { method: 'PUT' , params: {id: '@id'} },
        delete: { method: 'DELETE' , params: {id: '@id'} }
    })
});

// Routes
meals.config(function($routeProvider) {
	$routeProvider.when('/adminMeals', {templateUrl: urlPrefix + 'admin-meals.html', controller: 'MealListController'});
	$routeProvider.when('/adminNewMeal', {templateUrl: urlPrefix + 'admin-meals-new.html', controller: 'MealAddController'});
	$routeProvider.when('/adminEditMeal/:id', {templateUrl: urlPrefix + 'admin-meals-edit.html', controller: 'MealEditController'});
});

// Services
meals.service('mealService', function(MealsFactory) {
	var nextPk = 7;
	var allSelected = false;
	var list = [];

	this.indexForPK = function(pk) {
		for (var i = 0; i < list.length; i++) {
			var next = list[i];
			if (next.meals_pk == pk) {
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
                MealsFactory.delete({ id: list[i].meals_pk });
				list.splice(i, 1)
			}
		}
	}
    this.getAll = function() {
        list = MealsFactory.query();
        return list;
    }
    this.getItem = function(pk) {
        return MealsFactory.show({ id: pk });
    }
    this.addItem = function(item) {
        MealsFactory.update(item);
        //UsersFactory.create(item);
        list = MealsFactory.query();
        //item.user_pk = nextPk++; list.push(item);
    }
    this.removeItem = function(pk) {
        MealsFactory.delete({ id: pk });
        //list = UsersFactory.query();
        list.splice(this.indexForPK(pk), 1)
    }
    this.size = function() { return list.length; }
    this.isAllSelected = function() { return list.allSelected; }
    this.update = function(item) {
        MealsFactory.update(item);
        list = MealsFactory.query();
    }
});

// Cookbook Controllers
meals.controller('MealListController', function ($scope, $location, mealService) {
	$scope.mealList = mealService.getAll();
	$scope.allSelected = mealService.isAllSelected();
	
	$scope.goto = function (path) {
		$location.path(path);
	};
	
	$scope.edit = function (id) {
		$location.path('/adminEditMeal/' + id);
	};

	$scope.selectAll = function () {
		mealService.selectAll($scope.allSelected);
	};
		
	$scope.removeSelected = function () {
		mealService.removeSelected();
	};
		
	$scope.delete = function (id) {
		mealService.removeItem(id);
		$scope.mealList = mealService.getAll();
	};
		
});

meals.controller('MealEditController', function ($scope, $routeParams, $location, mainService, mealService) {
	$scope.mealToEdit = mealService.getItem($routeParams.id);
	$scope.backup = angular.copy($scope.mealToEdit);

	$scope.update = function () {
		mealService.update($scope.mealToEdit);
		mainService.setStatusBarText('Successfully updated meal "' + $scope.mealToEdit.name + '".');
		$location.path('/adminMeals');
	};
	
	$scope.cancel = function () {
		$scope.mealToEdit.name = $scope.backup.name;
		$location.path('/adminMeals');
	};
	
	$scope.delete = function () {
		mealService.removeItem($scope.mealToEdit.meals_pk);
		mainService.setStatusBarText('Successfully deleted meal "' + $scope.mealToEdit.name + '".');
		$location.path('/adminMeals');
	};
	
	$scope.goto = function (path) {
		$location.path(path);
	};	
});

meals.controller('MealAddController', function ($scope, $routeParams, $location, mealService) {
	$scope.mealToAdd = {'meals_pk': '', 'name': ''};

	$scope.addItem = function () {
		mealService.addItem($scope.mealToAdd);
		$location.path('/adminMeals');
	};
	
	$scope.cancel = function () {
		$location.path('/adminMeals');
	};
	
	$scope.goto = function (path) {
		$location.path(path);
	};	
});

angular.element(document).ready(function() { 
	angular.bootstrap(document.getElementById('meals'), ['meals']);
});
