var app = angular.module('w5app');

app.controllerProvider.register('users-admin', function($scope, $http) {
	
	$scope.roles = [];
	$scope.user = {};
		
	$scope.createUser = function()
	{
		 $http({
		        url: "admin-user-manager-app/user",
		        data: $scope.user,
		        method: 'POST'
		    }).success(function(data){
		    	angular.copy({}, $scope.user);
		    }).error(function(err){
		    	console.log(err);
		    });
	};
	
	$http.get('admin-user-manager-app/user/roles')
		.success(function(data) {
			$scope.roles.length = 0;
			for(var i = 0; i < data.length; i++)
			{
				$scope.roles.push(data[i]);
			}
			
		});
});

app.controllerProvider.register('list-users-utilities', function($scope, $http) {
	
	$scope.sort = {       
            sortingOrder : 'id',
            reverse : false
        };
	
	$scope.totalPages = 1;
	$scope.usersLength = 0;
    $scope.itemsPerPage = 5;
    $scope.currentPage = 0;
    $scope.users = [];
    
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
    
    $http.get('admin-user-manager-app/user')
	.success(function(data) {
		$scope.users.length = 0;
		for(var i = 0; i < data.length; i++)
		{
			$scope.users.push(data[i]);
		}
		$scope.usersLength = data.length;
		$scope.totalPages = Math.max(1, Math.ceil($scope.usersLength / $scope.itemsPerPage));
	});
});