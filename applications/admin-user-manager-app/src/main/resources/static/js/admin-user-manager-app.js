var app = angular.module('w5app');

app.controllerProvider.register('users-admin', function($scope, $http) {
	
	$scope.profiles = [];
	$scope.user = {profiles : []};
		
	$scope.createUser = function()
	{
		var tmpProfiles = $scope.user.profiles;
		for(var i = tmpProfiles.length -1; i >= 0; i--)
		{
			if(tmpProfiles[i] == null)
			{
				tmpProfiles.splice(i, 1);
			}
		}
		
		$http({
				url: "admin-user-manager-app/user",
		        data: $scope.user,
		        method: 'POST'
		    }).success(function(data){
		    	angular.copy({profiles : []}, $scope.user);
		    }).error(function(err){
		    	console.log(err);
		    });
	};
	
	$http.get('admin-user-manager-app/profile')
		.success(function(data) {
			$scope.profiles.length = 0;
			for(var i = 0; i < data.length; i++)
			{
				$scope.profiles.push(data[i]);
			}
			
		});
});

app.controllerProvider.register('list-users-utilities', function($scope, $http) {
	
	 $scope.createWidget = false;
	 
     $http({
         method: 'get',
         url: 'admin-user-manager-app/user'
     }).success(function (data, status) {
    	 
    	 var localData = [];
    	 
    	 for(var i = 0; i < data.length; i++)
    	 {
    		 var tmp = {};
    		 tmp['name'] = data[i]['name'];
    		 tmp['email'] = data[i]['name'];
    		 tmp['profiles'] = '';
    		 for(var j = 0; j < data[i]['profiles'].length; j++)
    		 {
    			 tmp['profiles'] = tmp['profiles'].concat(data[i]['profiles'][j]['name']);
    			 if(j < data[i]['profiles'].length - 1)
    			 {
    				 tmp['profiles'] = tmp['profiles'].concat(';');
    			 }
    		 }
    		 localData.push(tmp);
    	 }
    	 
    	 
         // prepare the data
         var source =
         {
             datatype: "json",
             datafields: [
                 { name: 'name', type: 'string' },
                 { name: 'email', type: 'string' },
                 { name: 'profiles', type: 'string' }
             ],
             id: 'userId',
             localdata: localData
         };
         var dataAdapter = new $.jqx.dataAdapter(source);
         $scope.gridSettings =
         {
             width: '100%',
             pageable: true,
             autoheight: true,
             autorowheight: true,
             source: dataAdapter,
             columnsresize: true,
             columns: [
               { text: 'Name', datafield: 'name' },

               { text: 'Email', datafield: 'email' },
               { text: 'Profiles', datafield: 'profiles', cellsRenderer: function (row, columnDataField, value) {
            	   var tmp = value.split(";");
            	   var r = '<div class="jqx-grid-cell-left-align" style="margin-top: 4px; margin-bottom: 4px;">';
            	   for(var x = 0; x < tmp.length; x++)
            	   {
            		   r = r.concat(tmp[x]);
            		   if(x < tmp.length - 1)
            		   {
            			   r = r.concat("<br/>");
            		   }
            	   }
                   return r.concat("</div>");
               	}
               }
             ]
         };
         $scope.createWidget = true;
     }).error(function (data, status) {
         alert(status);
     });
});