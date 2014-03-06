
var cookbooks = angular.module('editors', ['ngRoute', 'ngSanitize']);
 
var urlPrefix = '';

// Routes

cookbooks.config(function($routeProvider) {
	$routeProvider.when('/cseditor', {templateUrl: urlPrefix + 'editors-cseditor.html', controller: 'CSEditorController'});
	$routeProvider.otherwise({redirectTo: '/cseditor'});
});

// Services
cookbooks.service('editorsService', function() {
	var htmlFileToEdit = "/Users/mshoemake/publishing/Project Gutenberg (Non-Core Texts)/book_2000002/chapter004.html";
});

// Cookbook Controllers
cookbooks.controller('CSEditorController', function ($scope, $location, editorsService) {
	$scope.htmlFileToEdit = editorsService.htmlFileToEdit;
});

angular.element(document).ready(function() { 
	angular.bootstrap(document.getElementById('editors'), ['editors']);
});
