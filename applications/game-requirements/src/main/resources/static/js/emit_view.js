var app = angular.module('w5app');

app.controllerProvider.register('emit_view', function($scope, $http, $location) {
    
	$scope.judgeMoveId = $location.search()['judgeMoveId'];
	$scope.judgeMove = null;
	$scope.judgeFirstArgument = "";
	$scope.judgeSecondArgument = "";
	
	$http.get('game-requirements/judgemove/' + $scope.judgeMoveId)
	.success(function(data) {
		$scope.judgeMove = data;
	});
	
	$scope.selectWinner = function(judgeChoice){
		
		if(judgeChoice == "first"){
	    	$http.put('game-requirements/move/' + $scope.judgeMoveId + '/judgerequirement/' + $scope.judgeMove.move.firstRequirement.requirementId)
	    	.success(function(data) {
	    		
	    	});
		}
		
		if(judgeChoice == "second"){
			$http.put('game-requirements/move/' + $scope.judgeMoveId + '/judgerequirement/' + $scope.judgeMove.move.secondRequirement.requirementId)
	    	.success(function(data) {
	    		
	    	});
		}
		
		if(judgeChoice == "firstPersonal"){
			 $http({
			    	url: "game-requirements/argument/judge",
			    	data: {
			        content : $scope.judgeFirstArgument,
			       },
			       method: 'POST'
			   }).success(function(data){
				   $http.put('game-requirements/move/' + $scope.judgeMoveId + '/judgerequirement/' + $scope.judgeMove.move.firstRequirement.requirementId)
			    	.success(function(data) {
			    		
			    	});
			   }).error(function(err){
				   console.log(err);
			   });
		}
		
		if(judgeChoice == "secondPersonal"){
			$http({
		    	url: "game-requirements/argument/judge",
		    	data: {
		        content : $scope.judgeSecondArgument,
		       },
		       method: 'POST'
		   }).success(function(data){
			   $http.put('game-requirements/move/' + $scope.judgeMoveId + '/judgerequirement/' + $scope.judgeMove.move.secondRequirement.requirementId)
		    	.success(function(data) {
		    		
		    	});
		   }).error(function(err){
			   console.log(err);
		   });
		}
    };
    
});