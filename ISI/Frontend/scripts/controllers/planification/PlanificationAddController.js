'use strict';

transportLogisticsApp.controller('PlanificationAddController', ['$scope', '$http', '$location', 'DeliveryService', 'EmployeeService', 'PlanificationService', 'VehicleService',
    function($scope, $http, $location, DeliveryService, EmployeeService, PlanificationService, VehicleService) {

        $scope.availableStatusName = "Available";
        $scope.driverAccountTypeName = "Driver";

        $scope.planification = {};

        $scope.availableDeliveries = [];
        $scope.selectedDeliveryId = 0;
        $scope.selectedDelivery = {};

        $scope.availableVehicles = [];
        $scope.selectedVehicleId = 0;
        $scope.selectedVehicle = {};

        $scope.availableEmployees = [];
        $scope.selectedEmployeeId = 0;
        $scope.selectedEmployee = {};

        DeliveryService.findDeliveriesByStatusName($scope.availableStatusName).then(function (res) {
            $scope.availableDeliveries = res.data;
        }, function (err) {
            console.log('An error occurred while finding all available deliveries: ' + err.status);
        });

        VehicleService.findVehiclesByStatusName($scope.availableStatusName).then(function (res) {
            $scope.availableVehicles = res.data;
        }, function (err) {
            console.log('An error occurred while finding all available vehicles: ' + err.status);
        });

        EmployeeService.findEmployeesByAccountTypeNameAndStatusName($scope.driverAccountTypeName, $scope.availableStatusName).then(function (res) {
            $scope.availableEmployees = res.data;
        }, function (err) {
            console.log('An error occurred while finding all available drivers: ' + err.status);
        });

        $scope.updateSelectedDelivery = function() {
            if ($scope.selectedDeliveryId !== 0) {
                DeliveryService.findDelivery($scope.selectedDeliveryId).then(function (res) {
                    $scope.selectedDelivery = res.data;
                    $scope.planification.delivery = $scope.selectedDelivery;
                }, function (err) {
                    console.log('An error occurred while finding selected delivery: ' + err.status);
                });
            } else {
                $scope.selectedDelivery = {};
                $scope.planification.delivery = {};
            }
        };

        $scope.updateSelectedVehicle = function() {
            if ($scope.selectedVehicleId !== 0) {
                VehicleService.findVehicle($scope.selectedVehicleId).then(function (res) {
                    $scope.selectedVehicle = res.data;
                    $scope.planification.truck = $scope.selectedVehicle;
                }, function (err) {
                    console.log('An error occurred while finding selected vehicle: ' + err.status);
                });
            } else {
                $scope.selectedVehicle = {};
                $scope.planification.truck = {};
            }
        };

        $scope.updateSelectedEmployee = function() {
            if ($scope.selectedEmployeeId !== 0) {
                EmployeeService.findEmployee($scope.selectedEmployeeId).then(function (res) {
                    $scope.selectedEmployee = res.data;
                    $scope.planification.driver = $scope.selectedEmployee;
                }, function (err) {
                    console.log('An error occurred while finding selected employee: ' + err.status);
                });
            } else {
                $scope.selectedEmployee = {};
                $scope.planification.driver = {};
            }
        };

        $scope.save = function(planification) {
            PlanificationService.addPlanification(planification).then(function(res) {
                $scope.planification = res.data;
                $location.url('/coordinator/planification/all');
            }, function(err) {
                console.log('An error occurred while saving planification: ' + err.status);
            });
        };
    }]);
