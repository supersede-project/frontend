var app = angular.module('w5app');

app.controllerProvider.register('game', function($scope, $http, $location) {
	
	$scope.gameId = $location.search()['gameId'];
	$scope.game = null;
	$scope.ahpResult = null;
	
	$http.get('game-requirements/game/' + $scope.gameId)
	.success(function(data) {
		$scope.game = data;
	});

	 $scope.computeAHP = function(gameId){
		 $http.get('game-requirements/ahp/' + gameId)
	    	.success(function(data) {
	    		$scope.ahpResult = data;
    	});
	 };
	
	$scope.gameEnd = function(gameId){
		 $http.put('game-requirements/game/end/' + gameId)
	    	.success(function(data) {
	    		
	    	});
	 };
	 
	 $scope.requirementName = function(id)
	 {
		 for(var i = 0; i < $scope.game.requirements.length; i++)
		 {
			 if($scope.game.requirements[i].requirementId == id)
			 {
				 return $scope.game.requirements[i].name;
			 }
		 }
		 
		 return "";
	 }
});