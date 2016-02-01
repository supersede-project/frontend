var app = angular.module('w5app');

app.controllerProvider.register('move_creation', function($scope, $http) {
	
	$scope.valutationCriterias = [];
    $scope.users = []; 
    $scope.requirements = []; 
    $scope.selectedCriteria = null;
    $scope.selectedPlayerOne = null;
    $scope.selectedPlayerTwo = null;
    $scope.selectedFirstRequirement = null;
    $scope.selectedSecondRequirement = null;
    $scope.moveName = 'test';
    $scope.moveTimer = 3600;
    
    $http.get('game-requirements/criteria')
	.success(function(data) {
		$scope.valutationCriterias.length = 0;
		for(var i = 0; i < data.length; i++)
		{
			$scope.valutationCriterias.push(data[i]);
		}
	});
    	
	$http.get('game-requirements/user')
	.success(function(data) {
		$scope.users.length = 0;
		for(var i = 0; i < data.length; i++)
		{
			$scope.users.push(data[i]);
		}
	});
	
    $http.get('game-requirements/requirement')
	.success(function(data) {
		$scope.requirements.length = 0;
		for(var i = 0; i < data.length; i++)
		{
			$scope.requirements.push(data[i]);
		}
	});
    
    $scope.createMove = function(){
    	$http({
			url: "game-requirements/move",
	        data: {
	        	name : $scope.moveName,
	        	timer : $scope.moveTimer,
	        	firstRequirement : $scope.selectedFirstRequirement,
        		secondRequirement : $scope.selectedSecondRequirement,
        		firstPlayer : $scope.selectedPlayerOne,
        		secondPlayer : $scope.selectedPlayerTwo,
        		criteria : $scope.selectedCriteria
	        },
	        method: 'POST'
	    }).success(function(data){
	    	
	    }).error(function(err){
	    	console.log(err);
	    });
    };
    
});