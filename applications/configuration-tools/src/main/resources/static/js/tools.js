var app = angular.module('w5app');
app.controllerProvider.register('tools', function($scope, $http) {
    $scope.session_id = "";
    $scope.applications = {};

    $scope.getSession = function() {
        $http({
            url: "configuration-tools/session",
            method: 'GET'
        }).success(function(data) {
            $scope.session_id = data.id;
            }).error(function(err) {
                console.log(err);
            });
    };

    $scope.getApplications = function() {
        $http({
            url: "configuration-tools/applicationlist",
            method: 'GET'
        }).success(function(data) {
            $scope.applications = data;
            }).error(function(err) {
                console.log(err);
            });
    };

    $scope.getSession();
    $scope.getApplications();
});