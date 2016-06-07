var app = angular.module('w5app');

app.controllerProvider.register('users-admin', function($scope, $http, $location) {
	
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
				$location.url('admin-user-manager-app/list_users');
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
	
	$scope.validatorSettings = {
		hintType: 'label',
		animationDuration: 0,
		rules: [
			   { input: '#nameInput', message: 'Name is required!', action: 'keyup, blur', rule: 'required' },
			   { input: '#nameInput', message: 'Name must contain only letters!', action: 'keyup', rule: 'notNumber' },
			   {
				   input: '#birthInput', message: 'Birth date must be between 1/1/1900 and 1/1/2014.', action: 'valueChanged', rule: function (input, commit) {
					   var date = $('#birthInput').jqxDateTimeInput('value');
					   var result = date.getFullYear() >= 1900 && date.getFullYear() <= 2014;
					   // call commit with false, when you are doing server validation and you want to display a validation error on this field. 
					   return result;
				   }
			   },
			   { input: '#passwordInput', message: 'Password is required!', action: 'keyup, blur', rule: 'required' },
			   { input: '#passwordInput', message: 'Password must be between 4 and 12 characters!', action: 'keyup, blur', rule: 'length=4,12' },
			   { input: '#passwordConfirmInput', message: 'Password is required!', action: 'keyup, blur', rule: 'required' },
			   {
				   input: '#passwordConfirmInput', message: 'Passwords doesn\'t match!', action: 'keyup, focus', rule: function (input, commit) {
					   // call commit with false, when you are doing server validation and you want to display a validation error on this field. 
					   if (input.val() === $('#passwordInput').val()) {
						   return true;
					   }
					   return false;
				   }
			   },
			   { input: '#emailInput', message: 'E-mail is required!', action: 'keyup, blur', rule: 'required' },
			   { input: '#emailInput', message: 'Invalid e-mail!', action: 'keyup', rule: 'email' }]
	}

	// validate
	$scope.validate = function () {
		$scope.validatorSettings.apply('validate');
	}
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