var app = angular.module('w5app');

app.controllerProvider.register('judge_moves', function($scope, $http) {
    
	$scope.judgemoves = [];
	
	 $http.get('game-requirements/judgemove')
		.success(function(data) {
			$scope.judgemoves.length = 0;
			for(var i = 0; i < data.length; i++)
			{
				$scope.judgemoves.push(data[i]);
			}
		});
    
});