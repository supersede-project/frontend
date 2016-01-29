var app = angular.module('w5app');

app.controllerProvider.register('player_moves', function($scope, $http) {
    
    $scope.playerMoves = [];
	$scope.requirementsChoices = [];
    
    $http.get('game-requirements/playermove')
	.success(function(data) {
		for(var i = 0; i < data.length; i++)
		{
			$scope.playerMoves.push(data[i]);
		}
	});
	
	 $http.get('game-requirements/requirementchoice')
		.success(function(data) {
			$scope.requirementsChoices.length = 0;
			for(var i = 0; i < data.length; i++)
			{
				$scope.requirementsChoices.push(data[i]);
			}
		});
	 
	 $scope.setVote = function(playerVote, playerMoveId){
		 $http.put('game-requirements/playermove/' + playerMoveId + '/vote/' + playerVote)
	    	.success(function(data) {
	    		for(var i = 0; i < $scope.playerMoves.length; i++)
				{
					if($scope.playerMoves[i].playerMoveId == playerMoveId)
					{
						$scope.playerMoves[i].played = true;
					}
				}
    	});
	 };
    
});