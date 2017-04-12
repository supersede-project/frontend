var app = angular.module('w5app', ['ngRoute', 'jqwidgets'])
    .config(function ($routeProvider, $httpProvider, $controllerProvider, $compileProvider, $filterProvider, $provide) {

    $routeProvider.when('/', {
        templateUrl : 'home.html',
        controller : 'main_home'
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
    app.compileProvider = $compileProvider;
    app.routeProvider = $routeProvider;
    app.filterProvider = $filterProvider;
    app.provide = $provide;
});

app.controller('navigation', function($rootScope, $scope, $http, $location, $route, $interval) {
    $scope.tab = function(route) {
        return $route.current && route === $route.current.controller;
    };

    $rootScope.notificationsCount = 0;
    $rootScope.notifications = [];
    $rootScope.profiles = [];
    $rootScope.applications = [];
    $rootScope.currentApplication = {pages: []};
    $rootScope.roles = [];
    $rootScope.tenants = [];
    $rootScope.selectedTenant = "";
    $rootScope.user = {};
    $rootScope.currentLang = "any";
    $rootScope.langs = [];

    var authenticate = function (credentials, callback) {

        var headers = credentials ? {
            authorization: "Basic " + btoa(credentials.username + ":" + credentials.password)
        } : {};

        headers.TenantId = $scope.selectedTenant;

        $http.get('user', {
            headers: headers
        }).success(function (data) {
            if (data.name) {
                $rootScope.authenticated = true;
                $rootScope.roles = data.authorities;
                $rootScope.user = data.principal;

                for (var i = 0; i < data.authorities.length; i++) {
                    $rootScope.profiles.push(data.authorities[i].authority.substring(5));
                }

                getCurrentLang();
                getAllLangs();
                getApplications();
            }
            else {
                cleanAuth();
            }
            callback && callback($rootScope.authenticated);
        }).error(function () {
            cleanAuth();
            callback && callback(false);
        });
    };

    var getCurrentLang = function () {
        $http.get('locale/current').success(function (data) {
            $rootScope.currentLang = data.lang;
        });
    };

    var getAllLangs = function () {
        $http.get('locale').success(function (data) {
            for (var i = 0; i < data.length; i++) {
                $rootScope.langs[i] = data[i];
            }
        });
    };

    $scope.setCurrentLang = function () {
        var lang = this.l.lang;
        $http.put('locale/current', {}, { params: { lang: lang } }).success(function (data) {
            window.location.reload();
        });
    };

    var getApplications = function () {
        $http.get('application/page').success(function (data) {
            $rootScope.applications = [];

            for (var i = 0; i < data.length; i++) {
                $rootScope.applications[i] = data[i];
            }
        });
    };

    var cleanAuth = function () {
        $rootScope.authenticated = false;
        $rootScope.profiles = [];
        $rootScope.applications = [];
        $rootScope.user = {};
    };

    authenticate();

    $scope.credentials = {};
    $scope.login = function() {
        authenticate($scope.credentials, function (authenticated) {
            if (authenticated) {
                console.log("Login succeeded");
                $location.path("/");
                $scope.error = false;
                $rootScope.authenticated = true;
            }
            else {
                console.log("Login failed");
                $location.path("/login");
                $scope.error = true;
                cleanAuth();
            }
        });
    };

    $scope.logout = function () {
        $http.post('logout', {}).success(function () {
            cleanAuth();
            $location.path("/");
        }).error(function (data) {
            console.log("Logout failed");
            cleanAuth();
            $location.path("/");
        });
    };

    $scope.checkApplicationUrl = function () {
        for (var i = 0; i < $rootScope.applications.length; i++) {
            var application = $rootScope.applications[i];
            var test = '/' + application.applicationName + '/';

            if ($location.url().slice(0, test.length) == test) {
                $rootScope.currentApplication = application;
                return true;
            }
        }

        $rootScope.currentApplication = { pages: [] };
        return false;
    };

    var getTenants = function () {
        $http.get('tenant').success(function (data) {
            angular.copy(data, $rootScope.tenants);
            $rootScope.selectedTenant = $rootScope.tenants[0];
        });
    };

    getTenants();

    $scope.navigationMenuSettings =
    {
        width: '90%',
        height: '30px'
    };
});

app.controller('notifications', function($scope, $http) {

    $scope.createWidget = false;
    $scope.notifications = [];
    $scope.dataAdapter = {};

    $http.get('notification', {params:
        {
            toRead : false
        }
    }).success(function (data, status) {
        $scope.notifications.length = 0;

        for (var i = 0; i < data.length; i++)
        {
            $scope.notifications.push(data[i]);
        }

        // prepare the data
        var source =
        {
            datatype: "json",
            datafields: [
                { name: 'notificationId', type: 'int' },
                { name: 'message', type: 'string' },
                { name: 'link', type: 'string' },
                { name: 'read', type: 'bool' },
                { name: 'emailSent', type: 'bool' },
                { name: 'creationTime', type: 'int' }
            ],
            id: 'notificationId',
            localdata: $scope.notifications
        };

        $scope.dataAdapter = new $.jqx.dataAdapter(source);
        $scope.notificationsSettings =
        {
            width: '100%',
            pageable: true,
            autoheight: true,
            autorowheight: true,
            source: $scope.dataAdapter,
            columnsresize: true,
            columns: [
                { text: 'Read', datafield: 'read', width: 50,
                    cellsRenderer: function (row, columnDataField, value) {
                        var data = $('#notificationsGrid').jqxGrid('getrowdata', row);
                        var r = '<jqx-check-box style="margin-top: 6px; margin-left: 12px;" jqx-checked="' +
                            value + '" ng-click="readNotification(' + data.notificationId + ')"></jqx-check-box>';

                        return r;
                    }
                },
                { text: 'Message', datafield: 'message' },
                { text: 'Link', datafield: 'link', width: 90,
                    cellsRenderer: function (row, columnDataField, value) {
                        var data = $('#notificationsGrid').jqxGrid('getrowdata', row);
                        var r = '<div></div>';
                        if (value)
                        {
                            r = '<jqx-button style="margin-top: 3px; margin-left: 6px;" ng-click="openNotificationLink(' + data.notificationId + ', \'' + data.link + '\')">Open Link</jqx-button>';
                        }

                        return r;
                    }
                },
                { text: 'Delete', datafield: 'notificationId', width: 65,
                    cellsRenderer: function (row, columnDataField, value) {
                        var r = '<jqx-button style="margin-top: 3px; margin-left: 6px;" ng-click="deleteNotification(' + value + ')">Delete</jqx-button>';

                        return r;
                    }
                }
            ]
        };
        $scope.createWidget = true;
     }).error(function (data, status) {
         alert(status);
     });

    $scope.readNotification = function (notificationId) {
        $http.put('notification/' + notificationId + '/read').success(function (data) {

        });

        for (var i = 0; i < $scope.notifications.length; i++) {
            if ($scope.notifications[i].notificationId == notificationId) {
                $scope.notifications[i].read = true;
                break;
            }
        }

        $scope.dataAdapter.dataBind();
    };

    $scope.openNotificationLink = function (notificationId, link) {
        $scope.readNotification(notificationId);
        window.location.href = link;
    };

    $scope.deleteNotification = function (notificationId) {
        $http.delete('notification/' + notificationId).success(function (data) {

        });

        for (var i = 0; i < $scope.notifications.length; i++) {
            if ($scope.notifications[i].notificationId == notificationId) {
                $scope.notifications.splice(i, 1);
                break;
            }
        }

        $scope.dataAdapter.dataBind();
    };
});

var numGadgetRendered = 0;

app.directive('renderGadgets', [ '$timeout', function($timeout) {
    return function(scope, element, attrs) {
        numGadgetRendered++;
        if (numGadgetRendered == scope.gadgets.length){
            $('#docking').jqxDocking({ orientation: 'horizontal', mode: 'docked' });

            $timeout(function() {
                for (var i = 0; i < numGadgetRendered; i++)
                {
                    g = $('#gadget' + i);
                    g.css({ 'height': "auto" });
                    var children = g.children();
                    children.css({ 'height': "auto" });
                    $('#gadget' + i + 'Content').css({ 'height': "auto" });
                }

                numGadgetRendered = 0;
            }, 1);
        }
    };
}]);

app.controller('dashboard', function($scope, $http) {

    $scope.gadgets = [];
    $scope.availableGadgets = [];
    $scope.panels = [];

    $http.get('gadget/panel').success(function(data)
    {
        for(var i = 0; i < data; i++)
        {
            $scope.panels.push(i);
        }
    });

    $http.get('gadget').success(function(data)
    {
        $scope.gadgets.length = 0;
        for(var i = 0; i < data.length; i++)
        {
            $scope.gadgets.push(data[i]);
        }
    });

    var addGadget = function (applicationName, applicationGadget) {
        var tmp = {};
        tmp.applicationName = applicationName;
        tmp.gadgetName = applicationGadget;
        tmp.panel = 0;

        $scope.gadgets.push(tmp);

        $scope.$apply();

        var i = $scope.gadgets.length - 1;
        $('#docking').jqxDocking('addWindow', 'gadget' + (i), 'default', 0, 0);

        g = $('#gadget' + i);
        g.css({ 'height': "auto" });
        var children = g.children();
        children.css({ 'height': "auto" });
        $('#gadget' + i + 'Content').css({ 'height': "auto" });
    };

    $scope.save = function () {
        var toSave = [];
        for (var i = 0; i < $scope.panels.length; i++) {
            var divs = $("#panel" + i).children(':visible');
            for (var j = 0; j < divs.length; j++) {
                if (divs[j].id) {
                    if (divs[j].id.startsWith("gadget")) {
                        var id = divs[j].id.replace("gadget", "");
                        var tmp = JSON.parse(JSON.stringify($scope.gadgets[id]));
                        tmp.panel = i;
                        toSave.push(tmp);
                    }
                }
            }
        }

        $http({
            url: "gadget",
            data: toSave,
            method: 'POST'
        }).success(function (data) {

        }).error(function (err) {
            console.log(err);
        });
    };

    $scope.modalAddGadgetSettings = {
        height: 800, width: 1024, draggable: false,
        resizable: false, isModal: true, autoOpen: false, modalOpacity: 0.3
    };

    // show button click handler.
    $scope.showModalAddGadget = function () {
        $scope.modalAddGadgetSettings.apply('open');
        $("#availGadgets").jqxDataTable('render');
    };

    $scope.availGadgetsSettings =
    {
        width: 790,
        source:   new $.jqx.dataAdapter({
            dataType: "json",
            dataFields: [
                { name: 'applicationName', type: 'string' },
                { name: 'applicationGadget', type: 'string' }
            ],
            id: 'id',
            url: "gadget/available"
        }),
        sortable: false,
        pageable: true,
        pageSize: 3,
        pagerButtonsCount: 5,
        enableHover: false,
        selectionMode: 'none',
        columns: [
            {
                text: 'Gadgets', align: 'left', dataField: 'model',
                cellsRenderer: function (row, column, value, rowData) {
                    var container = "<div>";

                    container += '<div style="float: left;"><img width=160 height=120 style="display: block;" src="broken"/>';
                    container += "</div>";

                    container += '<div  style="float: left; margin: 10px;">' + rowData.applicationName +
                        " " + rowData.applicationGadget + "</div>";

                    container += '<div  style="float: right; margin: 10px;">';
                    container += "<jqx-button onclick=\"addGadget('" + rowData.applicationName + "', '" +
                        rowData.applicationGadget + "')\">Add</jqx-button>";
                    container += "</div>";

                    container += "</div>";
                    return container;
                }
            }
        ]
    };

    $scope.columnsButtonContent = function (args) {
        var dropDownContent = '<div style="position: relative; margin-left: 3px; margin-top: 5px;">Columns</div>';
        args.instance.setContent(dropDownContent);
    };

    $scope.setColumns = function (event) {
        if (event.args.checked) {
            var newColumns = event.target.id.replace("columns", "");
            var oldColumns = $scope.panels.length;

            if (newColumns != oldColumns) {
                $http({
                    url: "gadget/panel",
                    data: newColumns,
                    method: 'PUT'
                }).success(function (data) {
                    window.location.reload();
                }).error(function (err) {
                    console.log(err);
                });
            }
        }
    };
});

app.controller('main_home', function($scope, $http, $location) {

    $scope.goHome = function(appName, homePage) {
        $location.path('/' + appName + '/' + homePage);
    };
});

app.controller('header', function($scope, $http) {

});

app.controller('footer', function($scope, $http) {

});