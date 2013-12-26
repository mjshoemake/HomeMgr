
var cookbooks = angular.module('cookbooks', ['ngRoute', 'ngSanitize']);
 
var urlPrefix = 'file://localhost/Users/mshoemake/IdeaProjects/HomeMgr/prototype/';

// Routes

cookbooks.config(function($routeProvider) {
	$routeProvider.when('/adminCookbooks', {templateUrl: urlPrefix + 'admin-cookbooks.html', controller: 'CookbookListController'});
	$routeProvider.when('/adminMeals', {templateUrl: urlPrefix + 'admin-meals.html', controller: 'MealListController'});
	$routeProvider.otherwise({redirectTo: '/adminCookbooks'});

/*
	$routeProvider

		// route for the home page
		.when('/', {
			templateUrl : urlPrefix + 'admin-cookbooks.html',
			controller  : CookbookListController
		})
		.when('/adminCookbooks', {
			templateUrl : urlPrefix + 'admin-cookbooks.html',
			controller  : CookbookListController
		})
*/		
});

// Services
cookbooks.service('cookbookService', function() {
  var cookbookList = [
    {'name': '30 Minute Meals For Dummies'}, 
    {'name': 'allrecipes.com'}, 
    {'name': 'Betty Crocker'}, 
    {'name': 'Chris Carmichael\'s Fitness Cookbook'}, 
    {'name': 'Cooking Light Magazine'}, 
    {'name': 'Gluten Free and Easy'}, 
    {'name': 'Gran'}, 
    {'name': 'Grandma Grace'}, 
    {'name': 'Grandma Hilda'}, 
    {'name': 'Kraft Shredded Cheese Packet'}, 
    {'name': 'Lazy Day Cookin'}, 
    {'name': 'Mamaw Shoemake'}, 
    {'name': 'Mike\'s Test Cookbook'}, 
    {'name': 'Personal Cookbook'}, 
    {'name': 'Pillsbury Annual Recipes 2010'}, 
    {'name': 'The Gluten Free Bible'}, 
    {'name': 'The Great Potato Cookbook'}, 
    {'name': 'www.stephanieodea.com'}, 
    {'name': 'www.tasteofhome.com'} 
  ];
  this.getAll = function() { return cookbookList; }
  this.getItem = function(index) { return cookbookList[index]; }
  this.addItem = function(item) { cookbookList.push(item); }
  this.removeItem = function(item) { cookbookList.splice(cookbookList.indexOf(item), 1) }
  this.size = function() { return cookbookList.length; }
});

// Controllers
//AppController.$inject = ['$scope', '$route']
//cookbooks.controller('AppController', function ($scope, $route) {
//	$scope.$route = $route;
//} 

//cookbooks.controller('CookbookListController', ['$scope', 'cookbookService', function ($scope, cookbookService) {
cookbooks.controller('CookbookListController', function ($scope, cookbookService) {
	$scope.cookbookList = cookbookService.getAll();
});

