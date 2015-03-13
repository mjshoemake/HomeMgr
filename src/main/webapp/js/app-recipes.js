
var recipes = angular.module('recipes', ['ngRoute', 'ngSanitize', 'ngResource']);
 
var urlPrefix = '';
var lastPage = '';

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
	$routeProvider.when('/recipesByLetter/:filter', {templateUrl: urlPrefix + 'recipes-by-letter.html', controller: 'RecipeByLetterController'});
    $routeProvider.when('/recipesAll', {templateUrl: urlPrefix + 'recipes-all.html', controller: 'AllRecipesController'});
    $routeProvider.when('/recipesDetailed', {templateUrl: urlPrefix + 'recipes-details.html', controller: 'DetailedRecipesController'});
    $routeProvider.when('/recipesEdit/:id', {templateUrl: urlPrefix + 'recipes-edit.html', controller: 'RecipeEditController'});
    $routeProvider.when('/recipesNew', {templateUrl: urlPrefix + 'recipes-new.html', controller: 'RecipeAddController'});
});

// Services
recipes.service('recipeService', function(RecipesFactory, $http) {
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
	    numSelected = 0
        var itemsToDelete = ""
        for (var i = recipeList.length - 1; i >= 0; i--) {
            if (recipeList[i].selected) {
                if (numSelected > 0) {
                    itemsToDelete = itemsToDelete + ","
                }
                itemsToDelete = itemsToDelete + recipeList[i].recipes_pk
    	        recipeList.splice(i, 1)
                numSelected = numSelected + 1
            }
        }

        var deferred = $q.defer();
        RecipesFactory.delete({ id: itemsToDelete });
        deferred.resolve("done");
        return deferred.promise;
	}
    this.getAll = function($scope) {
        var result = RecipesFactory.query();

        var numRowsCol1
        if (result.length > 15) {
            numRowsCol1 = 15
        } else {
            numRowsCol1 = result.length
        }

        var numRowsCol2
        if (result.length > 30) {
            numRowsCol2 = 15
        } else {
            numRowsCol2 = result.length - 15
        }

        var numRowsCol3
        if (result.length > 45) {
            numRowsCol3 = 15
        } else {
            numRowsCol3 = result.length - 30
        }

        var index = 0
        var col1 = []
        var col2 = []
        var col3 = []

        for (i=0; i <= numRowsCol1-1; i++) {
            col1[i] = result[index]
            index++
        }
        for (i=0; i <= numRowsCol2-1; i++) {
            col2[i] = result[index]
            index++
        }
        for (i=0; i <= numRowsCol3-1; i++) {
            col3[i] = result[index]
            index++
        }
        $scope.col1 = col1
        $scope.col2 = col2
        $scope.col3 = col3
    }

    this.filter = function(filterText, letter, $scope, mainService) {
//        list = RecipesFactory.show(filterText);
        $http({
            method: 'GET',
            url: 'http://localhost:8080/homeMgr/recipes/' + filterText
        }).success(function(result) {
            $scope.letter = letter;
            $scope.recipeList = result;

            var numRowsCol1
            if (result.length > 15) {
                numRowsCol1 = 15
            } else {
                numRowsCol1 = result.length
            }

            var numRowsCol2
            if (result.length > 30) {
                numRowsCol2 = 15
            } else {
                numRowsCol2 = result.length - 15
            }

            var numRowsCol3
            if (result.length > 45) {
                numRowsCol3 = 15
            } else {
                numRowsCol3 = result.length - 30
            }

            var index = 0
            var col1 = []
            var col2 = []
            var col3 = []

            for (i=0; i <= numRowsCol1-1; i++) {
                col1[i] = result[index]
                index++
            }
            for (i=0; i <= numRowsCol2-1; i++) {
                col2[i] = result[index]
                index++
            }
            for (i=0; i <= numRowsCol3-1; i++) {
                col3[i] = result[index]
                index++
            }
            $scope.col1 = col1
            $scope.col2 = col2
            $scope.col3 = col3

        }).error(function(reason) {
            mainService.setStatusBarText('Failed to get the filtered list of recipes. "' + reason + '".');
        });
    }
    this.getItem = function(pk) {
        return RecipesFactory.show({ id: pk });
    }
    this.addItem = function(item) {
        var deferred = $q.defer();
        RecipesFactory.update(item);
        deferred.resolve("done");
        return deferred.promise;
    }
    this.removeItem = function(pk) {
        var deferred = $q.defer();
        RecipesFactory.delete({ id: pk });
        deferred.resolve("done");
        return deferred.promise;
    }
    this.size = function() { return recipeList.length; }
    this.isAllSelected = function() { return recipeList.allSelected; }
    this.update = function(item) {
        var deferred = $q.defer();
        RecipesFactory.update(item);
        deferred.resolve("done");
        return deferred.promise;
    }
});

recipes.controller('RecipeByLetterController', function ($scope, $rootScope, $routeParams, mainService, recipeService) {
    $rootScope.headerDisplay = "display: block;";
    $rootScope.bodyBackground = "";
    $rootScope.lastPage = '/recipesByLetter/' + $routeParams.filter;
    $rootScope.recipesPage = $rootScope.lastPage;
    recipeService.filter("name=" + $routeParams.filter + "*", $routeParams.filter, $scope, mainService);
    $scope.allSelected = recipeService.isAllSelected();
    $scope.edit = function (id) {
        $rootScope.goto('/recipesEdit/' + id);
    };
});

recipes.controller('AllRecipesController', function ($scope, $rootScope, $routeParams, mainService, recipeService) {
    $rootScope.headerDisplay = "display: block;";
    $rootScope.bodyBackground = "";
    $rootScope.lastPage = '/recipesAll';
    $rootScope.recipesPage = $rootScope.lastPage;
    recipeService.getAll($scope);
    $scope.edit = function (id) {
        $rootScope.goto('/recipesEdit/' + id);
    };
});

recipes.controller('DetailedRecipesController', function ($scope, $rootScope, $routeParams, mainService, recipeService) {
    $rootScope.headerDisplay = "display: block;";
    $rootScope.bodyBackground = "";
    $rootScope.lastPage = '/recipesDetailed';
    $rootScope.recipesPage = $rootScope.lastPage;
    //$scope.recipeList = recipeService.filter("name=" + $routeParams.filter + "*", $scope);
    //recipeService.filter("name=" + $routeParams.filter + "*", $routeParams.filter, $scope);
    recipeService.getAll($scope);

    $scope.allSelected = recipeService.isAllSelected();
    $scope.edit = function (id) {
        $rootScope.goto('/recipesEdit/' + id);
    };
});

recipes.controller('RecipeAddController', function ($scope, $rootScope, $routeParams, recipeService, mealService, foodCategoryService, cookbookService) {
	$scope.recipeToAdd = {'recipes_pk': '', 'name': '', 'directions': '', 'ingredients': '', 'nutrition': ''};
	$scope.cookbookList = cookbookService.getAll();
	$scope.foodCategoryList = foodCategoryService.getAll();
	$scope.mealList = mealService.getAll();	

	$scope.addItem = function () {
		recipeService.addItem($scope.recipeToAdd);
		$rootScope.goto($scope.recipesPage);
	};
	
	$scope.cancel = function () {
		$rootScope.goto($scope.recipesPage);
	};
});



// Cookbook Controllers
/*
recipes.controller('RecipesController', function ($scope, $rootScope, recipeService, loginService) {
    $rootScope.headerDisplay = "display: block;";
    $rootScope.bodyBackground = "";
    $rootScope.lastPage = '/recipesByLetter';
	$scope.recipeList = recipeService.getAll();
	$scope.allSelected = recipeService.isAllSelected();

    $scope.edit = function (id) {
        $rootScope.goto('/adminEditRecipe/' + id);
    };

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
*/

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

*/

angular.element(document).ready(function() { 
	angular.bootstrap(document.getElementById('recipes'), ['recipes']);
});
