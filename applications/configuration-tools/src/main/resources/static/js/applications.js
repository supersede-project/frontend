var app = angular.module('w5app');
app.controllerProvider.register('applications', function($scope, $http) {
    $scope.session_id = "";
    $scope.applications = {};

    $scope.getSession = function() {
        $http({
            url: "supersede-first-app/session",
            method: 'GET'
        }).success(function(data) {
            $scope.session_id = data.id;
            }).error(function(err) {
                console.log(err);
            });
    };

    $scope.getApplications = function() {
        $http({
            url: "supersede-first-app/applicationlist",
            method: 'GET'
        }).success(function(data) {
            console.log("Applications:");
            console.log(data);
            $scope.applications = data;
            }).error(function(err) {
                console.log(err);
            });
    };

    $scope.getSession();
    $scope.getApplications();
});