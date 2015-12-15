var app = angular.module('w5app');

app.controllerProvider.register('home', function($scope, $http, $rootScope) {
	
	$scope.loggedUser = $rootScope.user;
	$scope.user = null;
	
	$http.get('game-requirements/user/' + $scope.loggedUser.userId)
	.success(function(data) {
		$scope.user = data;
	});
	
});

app.controllerProvider.register('leaderboard', function($scope, $http) {
	
    $scope.users = [];
    $scope.usersCount = 0;
       
    $http.get('game-requirements/user')
	.success(function(data) {
		$scope.users.length = 0;
		for(var i = 0; i < data.length; i++)
		{
			$scope.users.push(data[i]);
		}
		 $scope.usersCount = data.length;
	});
});

app.controllerProvider.register('criterias_leaderboard', function($scope, $http) {
	
	$scope.valutationCriterias = [];
    $scope.criteriaUsers = []; 
    $scope.selectedCriteria = null;
    
    $http.get('game-requirements/criteria')
	.success(function(data) {
		$scope.valutationCriterias.length = 0;
		for(var i = 0; i < data.length; i++)
		{
			$scope.valutationCriterias.push(data[i]);
		}
	});
    
    $scope.filterCriteria = function(expected)
    {
    	alert(this.criteriaPoints);
    	alert(expected);
    	if(this.criteriaPoints.valutationCriteria.name == expected)
		{
    		return true;
		}
    	return false;
    }
    	
    $scope.selectedCriteriaChanged = function(){   	
    	$http.get('game-requirements/user/criteria/' + $scope.selectedCriteria.criteriaId)
		.success(function(data) {
			$scope.criteriaUsers.length = 0;
			for(var i = 0; i < data.length; i++)
			{
				$scope.criteriaUsers.push(data[i]);
			}
		});
    }
});

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

app.controllerProvider.register('user_moves', function($scope, $http) {
	
    $scope.moves = [];
    $scope.movesCount = 0;
    
    $http.get('game-requirements/move')
	.success(function(data) {
		$scope.moves.length = 0;
		for(var i = 0; i < data.length; i++)
		{
			$scope.moves.push(data[i]);
		}
		 $scope.movesCount = data.length;
	});
                 
});

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

app.controllerProvider.register('judge_moves', function($scope, $http) {
    
	$scope.judgemoves = [];
	
	 $http.get('game-requirements/judgemove')
		.success(function(data) {
			$scope.judgemoves.length = 0;
			for(var i = 0; i < data.length; i++)
			{
				$scope.judgemoves.push(data[i]);
			}
		});
    
});

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

app.controllerProvider.register('emit_view', function($scope, $http, $location) {
    
	$scope.judgeMoveId = $location.search()['judgeMoveId'];
	$scope.judgeMove = null;
	$scope.judgeChoice = "first";
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


