var app = angular.module('w5app');
app.controllerProvider.register('applications', function($scope, $http) {
    $scope.session = "";

    $scope.getSession = function() {
        $http({
            url: "supersede-first-app/session",
            method: 'GET'
        }).success(function(data) {
            $scope.session = data;
            }).error(function(err) {
                console.log(err);
            });
    };

    $scope.getSession();
});