'use strict';

transportLogisticsApp.controller('VehicleAddController', ['$scope', '$http', '$location', 'VehicleService', 'ColorService',
    function($scope, $http, $location, VehicleService, ColorService) {

        $scope.vehicle = {};
        $scope.colors = {};
        $scope.selectedColorId = 0;

        $scope.requiredErrorMessage = 'Please fill out this field';

        $scope.reset = function() {
            $scope.selectedColorId = 0;
            $scope.vehicle = {};
        };

        ColorService.findAllColors().then(function(res) {
            $scope.colors = res.data;
        }, function(err) {
            console.log('An error occurred while finding all colors: ' + err.status);
        });

        $scope.updateVehicleColor = function() {
            if ($scope.selectedColorId !== 0) {
                ColorService.findColor($scope.selectedColorId).then(function(res) {
                    $scope.vehicle.color = res.data;
                }, function(err) {
                    console.log('An error occurred while finding color: ' + err.status);
                });
            } else {
                $scope.vehicle.color = {};
            }
        };

        $scope.save = function(vehicle) {
            VehicleService.addVehicle(vehicle).then(function(res) {
                $scope.vehicle = res.data;
                $location.url('/coordinator/vehicle/all');
            }, function(err) {
                console.log('An error occurred while adding vehicle: ' + err.status);
            });
        };

    }]);
