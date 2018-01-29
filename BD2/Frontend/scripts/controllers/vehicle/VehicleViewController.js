'use strict';

transportLogisticsApp.controller('VehicleViewController', ['$scope', '$http', '$routeParams', '$location', 'VehicleService',
    function($scope, $http, $routeParams, $location, VehicleService) {

        VehicleService.findVehicle($routeParams.id)
            .then(function(res) {
                $scope.vehicle = res.data;
            }, function(err) {
                console.log('An error occurred while finding the vehicle: ' + err.status);
            });

        $scope.back = function() {
            $location.url('/coordinator/vehicle/all');
        };

    }]);