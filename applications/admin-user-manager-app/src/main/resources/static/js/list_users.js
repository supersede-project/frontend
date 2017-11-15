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

app.controllerProvider.register('list_users', function($scope, $http) {

    $scope.createWidget = false;

    $http.get('admin-user-manager-app/user')
    .success(function (data) {
        var localData = [];

        for (var i = 0; i < data.length; i++)
        {
            var tmp = {};
            tmp.id = data[i].userId;
            tmp.name = data[i].firstName + " " + data[i].lastName;
            tmp.email = data[i].email;
            tmp.profiles = '';

            for (var j = 0; j < data[i].profiles.length; j++)
            {
                tmp.profiles = tmp.profiles.concat(data[i].profiles[j].name);

                if (j < data[i].profiles.length - 1)
                {
                    tmp.profiles = tmp.profiles.concat(';');
                }
            }

            localData.push(tmp);
        }

        // prepare the data
        var source =
        {
            datatype: "json",
            datafields: [
                { name: 'id'},
                { name: 'name'},
                { name: 'email'},
                { name: 'profiles'}
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
                { text: 'Id', datafield: 'id' },
                { text: 'Name', datafield: 'name' },
                { text: 'Email', datafield: 'email' },
                { text: 'Profiles', datafield: 'profiles',
                    cellsRenderer: function (row, columnDataField, value) {
                        var tmp = value.split(";");
                        var r = '<div class="jqx-grid-cell-left-align" style="margin-top: 4px; margin-bottom: 4px;">';

                        for (var x = 0; x < tmp.length; x++)
                        {
                            r = r.concat(tmp[x]);

                            if (x < tmp.length - 1)
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
     }).error(function (err) {
         alert(err.message);
     });
});