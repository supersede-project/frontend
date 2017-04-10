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

app.controllerProvider.register('create_user', function($scope, $http, $location) {

    $scope.profiles = [];
    $scope.user = {profiles : []};

    $scope.errorMessage = undefined;

    $scope.createUser = function()
    {
        var tmpProfiles = $scope.user.profiles;

        for (var i = tmpProfiles.length -1; i >= 0; i--)
        {
            if (tmpProfiles[i] === null)
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
                $scope.errorMessage = err.message;
            });
    };

    $http.get('admin-user-manager-app/profile')
    .success(function(data) {
        $scope.profiles.length = 0;

        for (var i = 0; i < data.length; i++)
        {
            $scope.profiles.push(data[i]);
        }
    });

    $scope.validatorSettings = {
        hintType: 'label',
        animationDuration: 0,
        rules: [
            { input: '#usernameInput', message: 'First name is required!', action: 'keyup, blur', rule: 'required' },
            { input: '#firstNameInput', message: 'First name is required!', action: 'keyup, blur', rule: 'required' },
            { input: '#firstNameInput', message: 'Name must contain only letters!', action: 'keyup', rule: 'notNumber' },
            { input: '#lastNameInput', message: 'Last name is required!', action: 'keyup, blur', rule: 'required' },
            { input: '#lastNameInput', message: 'Name must contain only letters!', action: 'keyup', rule: 'notNumber' },
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
    };

    // validate
    $scope.validate = function () {
        $scope.validatorSettings.apply('validate');
    };
});