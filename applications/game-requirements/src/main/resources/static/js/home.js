var app = angular.module('w5app');

app.controllerProvider.register('home', function($scope, $http, $rootScope) {
	
	$scope.loggedUser = $rootScope.user;
	$scope.user = null;
	$scope.ahpResult = null;
	
	$http.get('game-requirements/user/' + $scope.loggedUser.userId)
	.success(function(data) {
		$scope.user = data;
	});
	
	$scope.computeAHP = function(){
		$http.get('game-requirements/ahp')
		.success(function(data) {
			$scope.ahpResult = data;
		});
	}
	
});