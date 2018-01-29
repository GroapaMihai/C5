'use strict';

transportLogisticsApp.controller('CoordinatorPlanificationViewController', ['$scope', '$http', '$routeParams', '$location', 'PlanificationService',
    function($scope, $http, $routeParams, $location, PlanificationService) {

        PlanificationService.findPlanification($routeParams.id)
            .then(function(res) {
                $scope.planification = res.data;
            }, function(err) {
                console.log('An error occurred while finding the planification: ' + err.status);
            });

        $scope.back = function() {
            $location.url('/coordinator/planification/all');
        };
    }]);
