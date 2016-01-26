var app = angular.module('w5app');

app.controllerProvider.register('player_moves', function($scope, $http) {
    
    $scope.playerMoves = [];
    
    $http.get('game-requirements/playermove')
	.success(function(data) {
		for(var i = 0; i < data.length; i++)
		{
			$scope.playerMoves.push(data[i]);
		}
	});
	
});