/*
   (C) Copyright 2015-2018 The SUPERSEDE Project Consortium

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at
     http://www.apache.org/licenses/LICENSE-2.0
   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

var app = angular.module('w5app');

app.controllerProvider.register('edit_user', function($scope, $http, $location) {

    $scope.createWidget = false;

    $scope.profiles = [];
    $scope.user = {profiles : []};

    $http.get('admin-user-manager-app/user')
    .success(function (data) {
        var localData = [];

        for (var i = 0; i < data.length; i++)
        {
            data[i].name = data[i].firstName + " " + data[i].lastName;
            localData.push(data[i]);
        }

        // prepare the data
        var source =
        {
            datatype: "json",
            datafields: [
                { name: 'userId'},
                { name: 'name'},
                { name: 'email'}
            ],
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
                { text: 'Id', datafield: 'userId' },
                { text: 'Name', datafield: 'name' },
                { text: 'Email', datafield: 'email' }
            ],
            ready: function()
            {
                $('#jqxGrid').bind('rowselect', function(event)  {
                    var current_index = event.args.rowindex;
                    var datarow = $('#jqxGrid').jqxGrid('getrowdata', current_index);

                    $http.get("admin-user-manager-app/user/" + datarow.userId)
                    .success(function (data) {
                        var tmp = data.profiles.splice(0, data.profiles.length);
                        data.profiles.length = 0;

                        for (var i = 0; i < tmp.length; i++)
                        {
                            data.profiles[tmp[i].profileId] = tmp[i];
                        }

                        $scope.user = data;
                    }).error(function(err){
                        alert(err.message);
                    });
                });
            }
        };
        $scope.createWidget = true;
    }).error(function (err) {
        alert(err.message);
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
        if (!$scope.user.userId)
        {
            return;
        }

        var tmpProfiles = $scope.user.profiles;
        for (var i = tmpProfiles.length -1; i >= 0; i--)
        {
            if (tmpProfiles[i] === null)
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
            alert(err.message);
        });
    };

    $scope.validatorSettings = {
        hintType: 'label',
        animationDuration: 0,
        rules: [
            { input: '#firstNameInput', message: 'First name is required!', action: 'keyup, blur', rule: 'required' },
            { input: '#firstNameInput', message: 'Name must contain only letters!', action: 'keyup', rule: 'notNumber' },
            { input: '#lastNameInput', message: 'Last name is required!', action: 'keyup, blur', rule: 'required' },
            { input: '#lastNameInput', message: 'Name must contain only letters!', action: 'keyup', rule: 'notNumber' },
            { input: '#emailInput', message: 'E-mail is required!', action: 'keyup, blur', rule: 'required' },
            { input: '#emailInput', message: 'Invalid e-mail!', action: 'keyup', rule: 'email' }]
    };

    $scope.passwordValidatorSettings = {
        hintType: 'label',
        animationDuration: 0,
        rules: [
            { input: '#oldPasswordInput', message: 'Old password is required!', action: 'keyup, blur', rule: 'required' },
            { input: '#newPasswordInput', message: 'New password is required!', action: 'keyup, blur', rule: 'required' },
            { input: '#newPasswordInput', message: 'Password must be between 4 and 12 characters!', action: 'keyup, blur', rule: 'length=4,12' },
            { input: '#confirmPasswordInput', message: 'Password is required!', action: 'keyup, blur', rule: 'required' },
            {
                input: '#confirmPasswordInput', message: 'Passwords don\'t match!', action: 'keyup, focus', rule: function (input, commit) {
                    // call commit with false, when you are doing server validation and you want to display a validation error on this field.
                    if (input.val() === $('#newPasswordInput').val()) {
                        return true;
                    }

                    return false;
                }
            }
        ]
    };

    // validate
    $scope.validate = function () {
        $scope.validatorSettings.apply('validate');
    };

    // validate password
    $scope.validatePassword = function () {
        $scope.passwordValidatorSettings.apply('validate');
    };
});