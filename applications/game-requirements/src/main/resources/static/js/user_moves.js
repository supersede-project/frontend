var app = angular.module('w5app');

app.controllerProvider.register('user_moves', function($scope, $http) {
	
    $scope.moves = [];
    $scope.movesCount = 0;
    
    $http.get('game-requirements/move')
	.success(function(data) {
		$scope.moves.length = 0;
		for(var i = 0; i < data.length; i++)
		{
			$scope.moves.push(data[i]);
		}
		 $scope.movesCount = data.length;
	});
                 
});