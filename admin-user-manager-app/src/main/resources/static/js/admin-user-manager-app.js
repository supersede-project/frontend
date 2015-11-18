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
		        console.log("OK", data)
		    }).error(function(err){"ERR", console.log(err)});
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