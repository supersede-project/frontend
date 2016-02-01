var app = angular.module('w5app');

app.controllerProvider.register('argue_view', function($scope, $http, $location, $rootScope) {
    	
	$scope.loggedUser = $rootScope.user;
	$scope.moveId = $location.search()['moveId'];
	$scope.argumentChoice = "existing";
	$scope.argumentContent = "";
	$scope.selectedArgument = null;
	$scope.arguments = [];
	$scope.move = null;
	
	$scope.isFirst = false;
	$scope.isSecond = false;
	
	$http.get('game-requirements/move/' + $scope.moveId)
	.success(function(data) {
		$scope.move = data;
		if($scope.move.firstPlayer.userId == $scope.loggedUser.userId){
			$scope.isFirst = true;
		}else{
			$scope.isSecond = true;
		}
	});
	
	$http.get('game-requirements/argument')
	.success(function(data) {
		$scope.arguments.length = 0;
		for(var i = 0; i < data.length; i++)
		{
			$scope.arguments.push(data[i]);
		}
	}); 
	 
	$scope.sendArgument = function(){
		 
		if($scope.argumentChoice == "personal"){
		    $http({
		    	url: "game-requirements/argument",
		    	data: {
		        content : $scope.argumentContent,
		       },
		       method: 'POST'
		   }).success(function(data){
			   // SET ARGUMENT IN JUDGE_MOVES
			   $http.put('game-requirements/judgemove/'+ $scope.moveId + '/argument/' + data.argumentId)
				.success(function(data) {
				}); 
				
		   }).error(function(err){
			   console.log(err);
		   });
		}else if($scope.argumentChoice == "existing"){
			// SET ARGUMENT IN JUDGE_MOVES
			$http.put('game-requirements/judgemove/'+ $scope.moveId + '/argument/' + $scope.selectedArgument.argumentId)
				.success(function(data) {
			}); 
		}
		 
	};
	
});