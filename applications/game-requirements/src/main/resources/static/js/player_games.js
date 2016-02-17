var app = angular.module('w5app');

app.controllerProvider.register('player_games', function($scope, $http, $location) {
    	
    $scope.playerGames = [];
       
    $http.get('game-requirements/game/of')
	.success(function(data) {
		for(var i = 0; i < data.length; i++)
		{
			$scope.playerGames.push(data[i]);
		}
	});
});