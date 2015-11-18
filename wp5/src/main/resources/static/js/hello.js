angular.module('hello', [ 'ngRoute' ]).config(function($routeProvider, $httpProvider) {

	$routeProvider.when('/', {
		templateUrl : 'home.html',
		controller : 'home'
	}).when('/login', {
		templateUrl : 'login.html',
		controller : 'navigation'
	}).when('/:name*', {
		templateUrl : function(urlattr){
            return urlattr.name + '.html';
        },
		controller : 'navigation'
	}).otherwise('/');

	$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';

}).controller('navigation', function($rootScope, $scope, $http, $location, $route) {

			$scope.tab = function(route) {
				return $route.current && route === $route.current.controller;
			};

			$rootScope.roles = [];
			$scope.userApplications = [];
			$scope.adminApplications = [];
			
			var authenticate = function(credentials, callback) {

				var headers = credentials ? {
					authorization : "Basic "
							+ btoa(credentials.username + ":"
									+ credentials.password)
				} : {};

				$http.get('user', {
					headers : headers
				}).success(function(data) {
					if (data.name) {
						$rootScope.authenticated = true;
						$rootScope.roles = data.authorities;
						if($scope.isAdmin())
						{
							getApplications("admin", $scope.adminApplications);
						}
						getApplications("user", $scope.userApplications);
					} else {
						cleanAuth();
					}
					callback && callback($rootScope.authenticated);
				}).error(function() {
					cleanAuth();
					callback && callback(false);
				});

			}

			$scope.isAdmin = function() {
				for(var i = 0; i < $rootScope.roles.length; i++) {
				    if ($rootScope.roles[i].authority == 'ADMIN') {
				        return true;
				    }
				}
				return false;
			}
			
			var getApplications = function(role, appl)
			{
				$http.get('application', {
					params: { role : role}
				}).success(function(data) {
					angular.extend(appl, data);
				});
			}
			
			var cleanAuth = function()
			{
				$rootScope.authenticated = false;
				$rootScope.roles = [];
				$scope.userApplications = [];
				$scope.adminApplications = [];
			}
			
			authenticate();

			$scope.credentials = {};
			$scope.login = function() {
				authenticate($scope.credentials, function(authenticated) {
					if (authenticated) {
						console.log("Login succeeded")
						$location.path("/");
						$scope.error = false;
						$rootScope.authenticated = true;
					} else {
						console.log("Login failed")
						$location.path("/login");
						$scope.error = true;
						cleanAuth();
					}
				})
			};

			$scope.logout = function() {
				$http.post('logout', {}).success(function() {
					$rootScope.authenticated = false;
					$rootScope.roles = [];
					$location.path("/");
				}).error(function(data) {
					console.log("Logout failed")
					$rootScope.authenticated = false;
					$rootScope.roles = [];
				});
			}

}).controller('home', function($scope, $http) {
	$http.get('wp5-test-app/resource').success(function(data) {
		$scope.greeting = data;
	})
});
