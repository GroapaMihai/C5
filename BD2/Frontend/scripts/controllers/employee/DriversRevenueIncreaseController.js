'use strict';

transportLogisticsApp.controller('DriversRevenueIncreaseController', ['$scope', '$http', '$location', '$window', 'EmployeeService',
    function($scope, $http, $location, $window, EmployeeService) {
        $scope.salaryIncreaseSuccessMessage = "Salaries updated successfully!";
        $scope.increaseTypes = ['Fixed Value', 'Percentage'];
        $scope.operators = ['AND', 'OR'];
        $scope.eligibleDrivers = [];

        $scope.revenueIncrease = {};
        $scope.revenueIncrease.salaryIncreaseType = 0;
        $scope.revenueIncrease.commIncreaseType = 0;
        $scope.revenueIncrease.operator = 0;

        $scope.listEligibleDrivers = function() {
            EmployeeService.findEligibleDrivers($scope.revenueIncrease).then(function(res) {
                $scope.eligibleDrivers = res.data;
            }, function(err) {
                console.log('An error occurred while finding eligible drivers: ' + err.status);
            });
        };

        $scope.applyIncrease = function() {
            EmployeeService.increaseDriversRevenue($scope.revenueIncrease).then(function(res) {
                $window.alert($scope.salaryIncreaseSuccessMessage);
                $scope.eligibleDrivers = res.data;
            }, function(err) {
                console.log('An error occurred while increasing revenues: ' + err.status);
            });
        };

        $scope.reset = function() {
            $scope.revenueIncrease = {};
            $scope.revenueIncrease.salaryIncreaseType = 0;
            $scope.revenueIncrease.commIncreaseType = 0;
            $scope.revenueIncrease.operator = 0;
        };
    }]);