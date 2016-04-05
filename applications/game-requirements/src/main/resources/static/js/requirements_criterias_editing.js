var app = angular.module('w5app');

app.controllerProvider.register('requirements_criterias_editing', function($scope, $http) {
		
	$scope.valutationCriterias = [];
	$scope.requirements = [];
	$scope.criteriaName = undefined;
	$scope.criteriaDescription = undefined;
	$scope.requirementName = undefined;
	$scope.requirementDescription = undefined;
	
	getCriterias = function () {
	 $http.get('game-requirements/criteria')
		.success(function(data) {
			for(var i = 0; i < data.length; i++)
			{
				$scope.valutationCriterias.push(data[i]);
			}
		});
	};
	
	getRequirements = function () {
		$http.get('game-requirements/requirement')
		.success(function(data) {
			for(var i = 0; i < data.length; i++)
			{
				$scope.requirements.push(data[i]);
			}
		});	
	};
	
	getCriterias();
	getRequirements();	 
	 
	$scope.createCriteria = function () {
		 $http.put('game-requirements/criteria/create/' + $scope.criteriaName + '/description/' + $scope.criteriaDescription)
			.success(function(data) {
				$scope.valutationCriterias = [];
				getCriterias();
				$scope.criteriaName = undefined;
				$scope.criteriaDescription = undefined;
			});
    };
    
    $scope.createRequirement = function () {
		 $http.put('game-requirements/requirement/create/' + $scope.requirementName + '/description/' + $scope.requirementDescription)
			.success(function(data) {
				$scope.requirements = [];
				getRequirements();
				$scope.requirementName = undefined;
				$scope.requirementDescription = undefined;
			});
   };
   
   $scope.deleteCriteria = function (criteriaId) {
		 $http.put('game-requirements/criteria/delete/' + criteriaId)
			.success(function(data) {
				if(data == true){
					$scope.valutationCriterias = [];
					getCriterias();
				}
			});
  };
});