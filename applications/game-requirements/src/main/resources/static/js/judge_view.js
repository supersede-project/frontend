var app = angular.module('w5app');

app.controllerProvider.register('judge_view', function($scope, $http, $location) {
	
	$scope.judgeMoveId = $location.search()['judgeMoveId'];
	$scope.judgeMove = null;
	
	$http.get('game-requirements/judgemove/' + $scope.judgeMoveId)
	.success(function(data) {
		$scope.judgeMove = data;
	});
	
	$scope.needArguments = function(){
		$http.put('game-requirements/judgemove/'+ $scope.judgeMoveId + '/needarguments')
		.success(function(data) {
		});
    };
    
    $scope.addRequirement = function(selectedRequirementId){
    	
    	$http.put('game-requirements/move/' + $scope.judgeMoveId + '/judgechooserequirement/' + selectedRequirementId)
    	.success(function(data) {
    		
    	});
    };
    
});