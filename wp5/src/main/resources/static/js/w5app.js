var app = angular.module('w5app', [ 'ngRoute' ]).config(function($routeProvider, $httpProvider, $controllerProvider, $compileProvider, $filterProvider, $provide) {

	$routeProvider.when('/', {
		templateUrl : 'home.html',
		controller : 'home'
	}).when('/login', {
		templateUrl : 'login.html',
		controller : 'navigation'
	}).when('/:name*', {
		templateUrl : function(urlattr){
            return urlattr.name + '.html';
        }
	}).otherwise('/');

	$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
	
	app.controllerProvider = $controllerProvider;
    app.compileProvider    = $compileProvider;
    app.routeProvider      = $routeProvider;
    app.filterProvider     = $filterProvider;
    app.provide            = $provide;

});

app.controller('navigation', function($rootScope, $scope, $http, $location, $route, $interval) {

			$scope.tab = function(route) {
				return $route.current && route === $route.current.controller;
			};

			$rootScope.notificationsCount = 0;
			$rootScope.profiles = [];
			$rootScope.applications = [];
			$rootScope.roles = [];
			
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
						getProfiles();
					} else {
						cleanAuth();
					}
					callback && callback($rootScope.authenticated);
				}).error(function() {
					cleanAuth();
					callback && callback(false);
				});

			}

			$scope.hasProfile = function(profile) {
				for(var i = 0; i < $rootScope.profiles.length; i++) {
				    if ($rootScope.profiles[i].authority == profile) {
				        return true;
				    }
				}
				return false;
			}
			
			var getApplications = function(profile)
			{
				$http.get('application', {
					params: { profileId : profile.profileId}
				}).success(function(data) {
					$rootScope.applications[profile.name] = data;
				});
			}
			
			var getProfiles = function()
			{
				$http.get('profile')
				.success(function(data) {
					//clean
					$rootScope.profiles.length = 0;
					angular.copy({}, $rootScope.applications);
					
					for(var i = 0; i < data.length; i++)
					{
						$rootScope.profiles.push(data[i]);
						getApplications(data[i]);
					}
					
				});
			}
			
			var cleanAuth = function()
			{
				$rootScope.authenticated = false;
				$rootScope.profiles = [];
				$rootScope.applications = [];
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
					cleanAuth();
					$location.path("/");
				}).error(function(data) {
					console.log("Logout failed")
					cleanAuth();
					$location.path("/");
				});
			}
			
			var stop;
			
			stop = $interval(function() {
				if($rootScope.authenticated)
				{
					$http.get('notification/count').success(function(data) {
						$rootScope.notificationsCount = data;
					});
				}
	        	}, 1000);
			

});

app.controller('home', function($scope, $http) {
	$http.get('wp5-test-app/resource').success(function(data) {
		$scope.greeting = data;
	})
});
