
var users = angular.module('users', ['ngRoute', 'ngSanitize']);
 
var urlPrefix = '';

// Routes
users.config(function($routeProvider) {
	$routeProvider.when('/adminUsers', {templateUrl: urlPrefix + 'admin-users.html', controller: 'UserListController'});
	$routeProvider.when('/adminNewUser', {templateUrl: urlPrefix + 'admin-users-new.html', controller: 'UserAddController'});
	$routeProvider.when('/adminEditUser/:id', {templateUrl: urlPrefix + 'admin-users-edit.html', controller: 'UserEditController'});
});

// Services
users.service('userService', function() {
	var nextPk = 7;
	var allSelected = false;
	var list = [
		{'selected': false, 'user_pk': '1', 'username': 'mjshoemake'}, 
		{'selected': false, 'user_pk': '2', 'username': 'mrshoemake'}, 
		{'selected': false, 'user_pk': '3', 'username': 'cashoemake'} 
	];
	this.indexForPK = function(pk) {
		for (var i = 0; i < list.length; i++) {
			var next = list[i];
			if (next.user_pk == pk) {
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
	this.addItem = function(item) { item.user_pk = nextPk++; list.push(item); }
	this.removeItem = function(pk) { list.splice(this.indexForPK(pk), 1) }
	this.size = function() { return list.length; }
	this.isAllSelected = function() { return list.allSelected; }
	this.update = function(item) { }
});

// Cookbook Controllers
users.controller('UserListController', function ($scope, $location, userService) {
	$scope.userList = userService.getAll();
	$scope.allSelected = userService.isAllSelected();
	
	$scope.goto = function (path) {
		$location.path(path);
	};
	
	$scope.edit = function (id) {
		$location.path('/adminEditUser/' + id);
	};

	$scope.selectAll = function () {
		userService.selectAll($scope.allSelected);
	};
		
	$scope.removeSelected = function () {
		userService.removeSelected();
	};
		
	$scope.delete = function (id) {
		ususerToEditerService.removeItem(id);
		$scope.userList = userService.getAll();
	};
});

meals.controller('UserEditController', function ($scope, $routeParams, $location, mainService, userService) {
	$scope.userToEdit = userService.getItem($routeParams.id);
	$scope.backup = angular.copy($scope.userToEdit);

	$scope.update = function () {
		userService.update($scope.userToEdit);
		mainService.setStatusBarText('Successfully updated user "' + $scope.userToEdit.username + '".');
		$location.path('/adminUsers');
	};
	
	$scope.cancel = function () {
		$scope.userToEdit.username = $scope.backup.username;
		$location.path('/adminUsers');
	};
	
	$scope.delete = function () {
		userService.removeItem($scope.userToEdit.user_pk);
		mainService.setStatusBarText('Successfully deleted user "' + $scope.userToEdit.username + '".');
		$location.path('/adminUsers');
	};
	
	$scope.goto = function (path) {
		$location.path(path);
	};	
});

meals.controller('UserAddController', function ($scope, $routeParams, $location, userService) {
	$scope.userToAdd = {'user_pk': '', 'name': ''};

	$scope.addItem = function () {
		userService.addItem($scope.userToAdd);
		$location.path('/adminUsers');
	};
	
	$scope.cancel = function () {
		$location.path('/adminUsers');
	};
	
	$scope.goto = function (path) {
		$location.path(path);
	};	
});

angular.element(document).ready(function() { 
	angular.bootstrap(document.getElementById('meals'), ['meals']);
});
