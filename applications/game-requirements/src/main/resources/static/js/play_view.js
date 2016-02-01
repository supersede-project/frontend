var app = angular.module('w5app');

app.controllerProvider.register('play_view', function($scope, $http, $location) {
	
	$scope.moveId = $location.search()['moveId'];
	$scope.move = null;
	
	$http.get('game-requirements/move/' + $scope.moveId)
	.success(function(data) {
		$scope.move = data;
	});
	 
    $scope.addRequirement = function(selectedRequirementId){
    	
    	$http.put('game-requirements/move/' + $scope.moveId + '/requirement/' + selectedRequirementId)
    	.success(function(data) {
    		
    	});
    };

});