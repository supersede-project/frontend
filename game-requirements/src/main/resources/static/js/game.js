//For intergration in the whole app
//var app = angular.module('w5app');

var app = angular.module('gameapp', [ 'ngRoute' ]).config(function($routeProvider, $httpProvider, $controllerProvider, $compileProvider, $filterProvider, $provide) {

	/*
	 * PROBLEM
	$routeProvider.when('/', {
		templateUrl : 'game_overview.html',
		controller : 'list-requirements'
	}).when('/:name*', {
		templateUrl : function(urlattr){
            return urlattr.name + '.html';
        }
	}).otherwise('/');
	*/
	$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
	
	app.controllerProvider = $controllerProvider;
    app.compileProvider    = $compileProvider;
    app.routeProvider      = $routeProvider;
    app.filterProvider     = $filterProvider;
    app.provide            = $provide;
});

app.controller('list-requirements', function($scope, $http) {
	
	$scope.sort = {       
            sortingOrder : 'id',
            reverse : false
        };
	
	$scope.totalPages = 1;
	$scope.requirementsLength = 0;
    $scope.itemsPerPage = 5;
    $scope.currentPage = 0;
    $scope.requirements = [];
    $scope.valutationCriterias = [];
    
    // ####################################
    // functions for the visualization of the table
    $scope.range = function () {
        var ret = [];
        var showPages = Math.min(5, $scope.totalPages);
        
        var start = Math.max(1, $scope.currentPage - Math.floor(showPages / 2));
        var end = Math.min($scope.totalPages, $scope.currentPage + Math.ceil(showPages / 2))
        
        if(start == 1)
        {
        	end = start + showPages - 1;
        }
        if(end == $scope.totalPages)
        {
        	start = end - showPages + 1;
        }
        
        for (var i = start; i < start + showPages; i++) {
            ret.push(i);
        }
        
        return ret;
    };
    
    $scope.prevPage = function () {
        if ($scope.currentPage > 0) {
            $scope.currentPage--;
        }
    };
    
    $scope.nextPage = function () {
        if ($scope.currentPage < $scope.totalPages - 1) {
            $scope.currentPage++;
        }
    };
    
    $scope.setPage = function () {
        $scope.currentPage = this.n -1;
    };    
    //####################################
    
    $http.get('/requirement')
	.success(function(data) {
		$scope.requirements.length = 0;
		for(var i = 0; i < data.length; i++)
		{
			$scope.requirements.push(data[i]);
		}
		$scope.requirementsLength = data.length;
		$scope.totalPages = Math.max(1, Math.ceil($scope.requirementsLength / $scope.itemsPerPage));
	});
    
    $http.get('/criteria')
	.success(function(data) {
		$scope.valutationCriterias.length = 0;
		for(var i = 0; i < data.length; i++)
		{
			$scope.valutationCriterias.push(data[i]);
		}
	});
});

app.controller('leaderboard', function($scope, $http) {
	
    $scope.users = [];
    $scope.usersCount = 0;
       
    $http.get('/user')
	.success(function(data) {
		$scope.users.length = 0;
		for(var i = 0; i < data.length; i++)
		{
			$scope.users.push(data[i]);
		}
		 $scope.usersCount = data.length;
	});
});


app.controller('criterias_leaderboard', function($scope, $http) {
	
	$scope.valuationCriterias = [];
    $scope.criteriaUsers = []; 
    $scope.selectedCriteria = null;
    
    $http.get('/criteria')
	.success(function(data) {
		$scope.valuationCriterias.length = 0;
		for(var i = 0; i < data.length; i++)
		{
			$scope.valuationCriterias.push(data[i]);
		}
	});
    	
    $scope.selectedCriteriaChanged = function(){   	
    	$http.get('/user/criteria/' + $scope.selectedCriteria.criteriaId)
		.success(function(data) {
			$scope.criteriaUsers.length = 0;
			for(var i = 0; i < data.length; i++)
			{
				$scope.criteriaUsers.push(data[i]);
			}
		});
    }    
});

app.controller('game_creation', function($scope, $http) {
	
	$scope.valuationCriterias = [];
    $scope.users = []; 
    $scope.requirements = []; 
    $scope.selectedCriteria = null;
    $scope.selectedPlayerOne = null;
    $scope.selectedPlayerTwo = null;
    $scope.selectedFirstRequirement = null;
    $scope.selectedSecondRequirement = null;
    $scope.gameName = '';
    $scope.gameTimer = '';
    
    $http.get('/criteria')
	.success(function(data) {
		$scope.valuationCriterias.length = 0;
		for(var i = 0; i < data.length; i++)
		{
			$scope.valuationCriterias.push(data[i]);
		}
	});
    	
	$http.get('/user')
	.success(function(data) {
		$scope.users.length = 0;
		for(var i = 0; i < data.length; i++)
		{
			$scope.users.push(data[i]);
		}
	});
	
    $http.get('/requirement')
	.success(function(data) {
		$scope.requirements.length = 0;
		for(var i = 0; i < data.length; i++)
		{
			$scope.requirements.push(data[i]);
		}
	});                
});


