'use strict';

transportLogisticsApp.controller('DriverPlanificationViewController', ['$scope', '$http', '$routeParams', '$location', '$window', 'PlanificationService',
    function($scope, $http, $routeParams, $location, $window, PlanificationService) {

        $scope.deliverSuccessMessage = "Delivered successfully!";
        $scope.planificationInTransit = false;

        PlanificationService.findPlanification($routeParams.id)
            .then(function(res) {
                $scope.planification = res.data;
                $scope.planificationInTransit = ($scope.planification.status.name === "In transit");
            }, function(err) {
                console.log('An error occurred while finding the planification: ' + err.status);
            });

        $scope.back = function() {
            $location.url('/driver/planification/all');
        };

        $scope.deliver = function() {
            PlanificationService.deliverPlanification($scope.planification).then(function(res) {
                $scope.planification = res.data;
                $window.alert($scope.deliverSuccessMessage);
                $location.url('/driver/planification/all');
            }, function(err) {
                console.log('An error occurred while delivering planification: ' + err.status);
            });
        };
    }]);
