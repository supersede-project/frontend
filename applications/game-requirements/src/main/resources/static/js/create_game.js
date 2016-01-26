var app = angular.module('w5app');

app.controllerProvider.register('create_game', function($scope, $http) {

    $scope.players = [];
    $scope.requirements = [];
    $scope.criterias = [];

    $scope.currentPlayer = undefined;
    $scope.currentRequirement= undefined;
    $scope.currentCriteria = undefined;
    
    $scope.game = {players : [], requirements: [], criterias: []};
    
    $scope.currentPage = 'page1';
    
    $scope.requirementsChoices = [];
    
    $scope.choices = {};
    
    $http.get('game-requirements/user?profile=PLAYER')
	.success(function(data) {
		for(var i = 0; i < data.length; i++)
		{
			$scope.players.push(data[i]);
		}
	});
    
    $http.get('game-requirements/requirement')
	.success(function(data) {
		for(var i = 0; i < data.length; i++)
		{
			$scope.requirements.push(data[i]);
		}
	});
    
    $http.get('game-requirements/criteria')
	.success(function(data) {
		for(var i = 0; i < data.length; i++)
		{
			$scope.criterias.push(data[i]);
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
    
    $scope.toggleSelection = function(array, item)
	{
	    var idx = array.indexOf(item);
	    if (idx > -1) {
	    	array.splice(idx, 1);
	    }
	    else {
	    	array.push(item);
	    }
	};
	
	$scope.nextPage = function()
	{
		if($scope.game.players.length > 0 &&
				$scope.game.requirements.length > 1 &&
				$scope.game.criterias.length > 1)
		{
			$scope.currentPage = 'page2';
		}
	}
	
	$scope.createGame = function()
	{
		$http({
			url: "game-requirements/game",
	        data: $scope.game,
	        method: 'POST',
	        params: {criteriaValues : $scope.choices}
	    }).success(function(data){
	    	$scope.game = {players : [], requirements: [], criterias: []};
	    	$scope.choices = {};
	    	$scope.currentPage = 'page1';
	    }).error(function(err){
	    	console.log(err);
	    });
	};
});