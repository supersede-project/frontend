var app = angular.module('w5app');

app.controllerProvider.register('judge', function($scope, $http) {
    
    $scope.judgeActs = [];
    
    $http.get('game-requirements/judgeact')
	.success(function(data) {
		for(var i = 0; i < data.length; i++)
		{
			$scope.judgeActs.push(data[i]);
		}
	});
	
});