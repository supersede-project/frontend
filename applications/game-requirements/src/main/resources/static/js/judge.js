var app = angular.module('w5app');

app.controllerProvider.register('judge', function($scope, $http) {
    
    $scope.judgeActs = [];
	$scope.requirementsChoices = [];
	$scope.votedMoves = 0;
    
    $http.get('game-requirements/judgeact')
	.success(function(data) {
		for(var i = 0; i < data.length; i++)
		{
			$scope.judgeActs.push(data[i]);
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
    
	 $scope.setVote = function(judgeVote, judgeActId){
		 $http.put('game-requirements/judgeact/' + judgeActId + '/vote/' + judgeVote)
	    	.success(function(data) {
	    		for(var i = 0; i < $scope.judgeActs.length; i++)
				{
					if($scope.judgeActs[i].judgeActId == judgeActId)
					{
						$scope.judgeActs[i].voted = true;
					}
				}
    	});
	 };
	 
	 $http.get('game-requirements/playermove/requirementsmatrixdata/' + $scope.judgeAct.requirementsMatrixData.requirementsMatrixDataId)
		.success(function(data) {
			for(var i = 0; i < data.length; i++)
			{
				if(data[i].value != null){
					$scope.votedMoves = $scope.votedMoves + 1;
				}			
			}
		});

});