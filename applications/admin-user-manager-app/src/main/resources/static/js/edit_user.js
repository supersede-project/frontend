var app = angular.module('w5app');

app.controllerProvider.register('edit_user', function($scope, $http, $location) {
	
	$scope.createWidget = false;

	$scope.profiles = [];
	$scope.user = {profiles : []};
	
	$http({
		method: 'get',
		url: 'admin-user-manager-app/user'
	}).success(function (data, status) {
		 
		var localData = [];
		
		for(var i = 0; i < data.length; i++)
		{
			data[i].name = data[i]['firstName'] + " " + data[i]['lastName'];
			localData.push(data[i]);
		}
		
		// prepare the data
		var source =
		{
			datatype: "json",
			datafields: [
			    { name: 'userId', type: 'int' },
			    { name: 'name', type: 'string' },
				{ name: 'email', type: 'string' }
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
			    { text: 'Email', datafield: 'email' }
			],
			ready: function()
			{
				$('#jqxGrid').bind('rowselect', function(event)  {
					var current_index = event.args.rowindex;
					var datarow = $('#jqxGrid').jqxGrid('getrowdata', current_index);
					
					$http({
						url: "admin-user-manager-app/user/" + datarow.userId,
						method: 'GET'
					}).success(function(data){
						var tmp = data.profiles.splice(0, data.profiles.length);
						data.profiles.length = 0;
						for(var i = 0; i < tmp.length; i++)
						{
							data.profiles[tmp[i].profileId] = tmp[i];
						}
						
						$scope.user = data;
					}).error(function(err){
						console.log(err);
					});
				});
			}
		};
		$scope.createWidget = true;
	}).error(function (data, status) {
		alert(status);
	});
	
	$http.get('admin-user-manager-app/profile')
		.success(function(data) {
			$scope.profiles.length = 0;
			for(var i = 0; i < data.length; i++)
			{
				$scope.profiles.push(data[i]);
			}
			
		});
	
	$scope.editUser = function()
	{
		if(!$scope.user.userId)
		{
			return;
		}
		
		var tmpProfiles = $scope.user.profiles;
		for(var i = tmpProfiles.length -1; i >= 0; i--)
		{
			if(tmpProfiles[i] == null)
			{
				tmpProfiles.splice(i, 1);
			}
		}
		
		$http({
				url: "admin-user-manager-app/user/" + $scope.user.userId,
				data: $scope.user,
				method: 'PUT'
			}).success(function(data){
				$location.url('admin-user-manager-app/edit_user');
			}).error(function(err){
				console.log(err);
			});
	};
	
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