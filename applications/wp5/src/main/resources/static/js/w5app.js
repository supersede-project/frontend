var app = angular.module('w5app', [ 'ngRoute' ]).config(function($routeProvider, $httpProvider, $controllerProvider, $compileProvider, $filterProvider, $provide) {

	$routeProvider.when('/', {
		templateUrl : 'dashboard.html',
		controller : 'dashboard'
	}).when('/dashboard', {
		templateUrl : 'dashboard.html',
		controller : 'dashboard'
	}).when('/login', {
		templateUrl : 'login.html',
		controller : 'navigation'
	}).when('/notifications', {
		templateUrl : 'notifications.html',
		controller : 'notifications'
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
			$rootScope.notifications = [];
			$rootScope.profiles = [];
			$rootScope.applications = [];
			$rootScope.roles = [];
			$rootScope.tenants = [];
			$rootScope.selectedTenant = "";
			$rootScope.user = {};
			$rootScope.currentLang = "any";
			$rootScope.langs = [];
			
			var authenticate = function(credentials, callback) {

				var headers = credentials ? {
					authorization : "Basic "
							+ btoa(credentials.username + ":"
									+ credentials.password)
				} : {};

				headers.TenantId = $scope.selectedTenant;
				
				$http.get('user', {
					headers : headers
				}).success(function(data) {
					if (data.name) {
						$rootScope.authenticated = true;
						$rootScope.roles = data.authorities;
						$rootScope.user = data.principal;
						for(var i = 0; i < data.authorities.length; i++)
						{
							$rootScope.profiles.push(data.authorities[i].authority.substring(5));
						}
						getCurrentLang();
						getAllLangs();
						getApplications();
					} else {
						cleanAuth();
					}
					callback && callback($rootScope.authenticated);
				}).error(function() {
					cleanAuth();
					callback && callback(false);
				});

			}
			
			var getCurrentLang = function()
			{
				$http.get('locale/current').success(function(data) {
					$rootScope.currentLang = data.lang;
				});
			}
			
			var getAllLangs = function()
			{
				$http.get('locale').success(function(data) {
					for(var i = 0;i < data.length; i++ )
					{
						$rootScope.langs[i] = data[i];
					}
				});
			}
			
			$scope.setCurrentLang = function()
			{
				var lang = this.l.lang;
				$http.put('locale/current', {}, {params:{lang : lang}}).success(function(data) {
					window.location.reload();
				});
			}
			
			var getApplications = function()
			{
				$http.get('application/page').success(function(data) {
					for(var i = 0;i < data.length; i++ )
					{
						$rootScope.applications.push(data[i]);
					}
				});
			}
			
			var cleanAuth = function()
			{
				$rootScope.authenticated = false;
				$rootScope.profiles = [];
				$rootScope.applications = [];
				$rootScope.user = {};
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
			
			$scope.openNotificationLink = function()
			{
				var notificationId = this.notif.notificationId;
				var link = this.notif.link;
				$http.put('notification/' + notificationId + '/read').
					success(function(data) {
						
					});
				window.location.href=link;
			}
			
			$scope.readNotification = function()
			{
				var notificationId = this.notif.notificationId;
				$http.put('notification/' + notificationId + '/read').
					success(function(data) {
						
					});
			}
			
			$scope.deleteNotification = function()
			{
				var notificationId = this.notif.notificationId;
				$http.delete('notification/' + notificationId).
					success(function(data) {
					
					});
			}
			
			var getTenants = function()
			{
				$http.get('tenant').success(function(data) {
					angular.copy(data, $rootScope.tenants);
					$rootScope.selectedTenant = $rootScope.tenants[0];
				});
			}
			
			getTenants();
			
			var stop;
			
			stop = $interval(function() {
				if($rootScope.authenticated)
				{
					$http.get('notification/count').success(function(data) {
						if($rootScope.notificationsCount != data)
						{
							$rootScope.notificationsCount = data;
							$http.get('notification').success(function(data) {
								//clean
								$rootScope.notifications.length = 0;
								angular.copy({}, $rootScope.notifications);
								
								for(var i = 0; i < data.length; i++)
								{
									$rootScope.notifications.push(data[i]);
								}
							});
						}
					});
				}
	        	}, 1000);
			

});

app.controller('notifications', function($scope, $http) {
	
	$scope.totalPages = 1;
	$scope.notificationsLength = 0;
    $scope.itemsPerPage = 5;
    $scope.currentPage = 0;
	$scope.notifications = [];
	
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
	
	$scope.getNotifications = function()
	{
		$http.get('notification', {params: 
				{
					toRead : false
				}
			}).
			success(function(data) {
				angular.copy({}, $scope.notifications);
				
				for(var i = 0; i < data.length; i++)
				{
					$scope.notifications.push(data[i]);
				}
				$scope.notificationsLength = data.length;
				$scope.totalPages = Math.max(1, Math.ceil($scope.notificationsLength / $scope.itemsPerPage));
		});
	}
	
	$scope.readNotification = function()
	{
		var notificationId = this.notif.notificationId;
		$http.put('notification/' + notificationId + '/read').
			success(function(data) {
				
			});
		
		for(var i = 0; i < $scope.notifications.length; i++)
		{
			if($scope.notifications[i].notificationId == notificationId)
			{
				$scope.notifications[i].read = true;
				break;
			}
		}
	}
	
	$scope.openNotificationLink = function()
	{
		var notificationId = this.notif.notificationId;
		var link = this.notif.link;
		$http.put('notification/' + notificationId + '/read').
			success(function(data) {
				
			});
		window.location.href=link;
	}
	
	$scope.deleteNotification = function()
	{
		var notificationId = this.notif.notificationId;
		$http.delete('notification/' + notificationId).
			success(function(data) {
			
			});
		
		for(var i = 0; i < $scope.notifications.length; i++)
		{
			if($scope.notifications[i].notificationId == notificationId)
			{
				$scope.notifications.splice(i, 1);
				break;
			}
		}
		$scope.notificationsLength--;
		$scope.totalPages = Math.max(1, Math.ceil($scope.notificationsLength / $scope.itemsPerPage));
		if($scope.totalPages == $scope.currentPage && $scope.currentPage != 0)
		{
			$scope.currentPage--;
		}
	}
	
	$scope.getNotifications();
	
});

app.controller('dashboard', function($scope, $http) {

	$scope.gadgets = [];
	$scope.availableGadgets = [];
	$scope.selectedAvailableGadgets = [];
	
	$http.get('application/availableGadget').success(function(data)
	{
		$scope.availableGadgets.length = 0;
		for(var i = 0; i < data.length; i++)
		{
			$scope.availableGadgets.push(data[i]);
		}
	});
	
	$http.get('application/userGadget').success(function(data)
	{
		$scope.gadgets.length = 0;
		for(var i = 0; i < data.length; i++)
		{
			var g = {'applicationName' : data[i].applicationName, 'gadgetName' : data[i].gadgetName, 'size' : data[i].size}
			$scope.gadgets.push(g);
		}
	});
	
	$scope.toggleSelection = function(gadget)
	{
	    var idx = $scope.selectedAvailableGadgets.indexOf(gadget);

	    // is currently selected
	    if (idx > -1) {
	      $scope.selectedAvailableGadgets.splice(idx, 1);
	    }

	    // is newly selected
	    else {
	      $scope.selectedAvailableGadgets.push(gadget);
	    }
	};
	
	$scope.addGadgets = function()
	{
		$('#modalGadgets').modal('hide');
		for(var i = 0; i < $scope.selectedAvailableGadgets.length; i++)
		{
			var sg = $scope.selectedAvailableGadgets[i];
			var g = {'applicationName' : sg.applicationName, 'gadgetName' : sg.applicationGadget, 'size' : 'small'}
			
			var contains = false;
			for(var j = 0; j < $scope.gadgets.length && !contains; j++)
			{
				if($scope.gadgets[j].applicationName == g.applicationName && $scope.gadgets[j].gadgetName == g.gadgetName)
				{
					contains = true;
				}
			}

			if(!contains)
			{
				$scope.gadgets.push(g);
			}
		}
		$scope.selectedAvailableGadgets.length = 0;
	}
	
	$scope.cancelAddGadgets = function()
	{
		$('#modalGadgets').modal('hide');
		$scope.selectedAvailableGadgets.length = 0;
	}
	
	$scope.removeGadget = function(index)
	{
		$scope.gadgets.splice(index, 1);
	}
	
	$scope.saveSelectedGadgets = function() 
	{
		$http({
			url: "application/userGadget",
	        data: $scope.gadgets,
	        method: 'POST'
	    }).success(function(data){
	    	$scope.gadgets.length = 0;
			for(var i = 0; i < data.length; i++)
			{
				var g = {'applicationName' : data[i].applicationName, 'gadgetName' : data[i].gadgetName, 'size' : data[i].size}
				$scope.gadgets.push(g);
			}
	    }).error(function(err){
	    	console.log(err);
	    });
	}
	
	$scope.allowDropGadget = function(ev, index) {
	    ev.preventDefault();
	}

	$scope.dragGadget = function(ev, index) {
	    $scope.dragFrom = parseInt(index.replace("gadget", ""));
	}

	$scope.dropGadget = function(ev, index) {
	    ev.preventDefault();
	    var dragTo = parseInt(index.replace("gadget", ""));
	    
	    //move from to
	    //$scope.gadgets.splice(dragTo, 0, $scope.gadgets.splice($scope.dragFrom, 1)[0]);
	    
	    //switch from to
	    var b = $scope.gadgets[$scope.dragFrom];
	    $scope.gadgets[$scope.dragFrom] = $scope.gadgets[dragTo];
	    $scope.gadgets[dragTo] = b;
	    
	    $scope.$apply();
	}

	$scope.shrinkGadget = function(index)
	{
		$scope.gadgets[index].size="small";
	}
	
	$scope.enlargeGadget = function(index)
	{
		$scope.gadgets[index].size="large";
	}
});
