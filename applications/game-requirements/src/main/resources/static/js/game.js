var app = angular.module('w5app');

app.controllerProvider.register('game', function($scope, $http, $location) {
	
	$scope.gameId = $location.search()['gameId'];
	$scope.game = null;
	
	$http.get('game-requirements/game/' + $scope.gameId)
	.success(function(data) {
		$scope.game = data;
	});
	
});