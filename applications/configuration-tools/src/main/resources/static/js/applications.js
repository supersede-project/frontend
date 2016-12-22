var app = angular.module('w5app');
app.controllerProvider.register('applications', function($scope, $http) {
    $scope.session_id = "";

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

    $scope.getSession();
});