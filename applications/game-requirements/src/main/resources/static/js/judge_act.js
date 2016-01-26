var app = angular.module('w5app');

app.controllerProvider.register('judge_act', function($scope, $http, $location) {
    
	$scope.judgeActId = $location.search()['judgeActId'];
    $scope.judgeAct = null;
	$scope.requirementsChoices = [];
	$scope.selectedRequirementsChoice = {selected:4};
	
	$http.get('game-requirements/judgeact/' + $scope.judgeActId)
	.success(function(data) {
		$scope.judgeAct = data;
	});
    
	 $http.get('game-requirements/requirementchoice')
		.success(function(data) {
			$scope.requirementsChoices.length = 0;
			for(var i = 0; i < data.length; i++)
			{
				$scope.requirementsChoices.push(data[i]);
			}
		});
	 
	 $scope.insertjudgeVote = function(judgeVote){
		 $http.put('game-requirements/judgeact/' + $scope.judgeActId + '/vote/' + judgeVote)
	    	.success(function(data) {
	    		
    	});
	 };
});