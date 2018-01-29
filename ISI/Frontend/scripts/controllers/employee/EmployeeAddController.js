'use strict';

transportLogisticsApp.controller('EmployeeAddController', ['$scope', '$http', '$location', 'EmployeeService', 'AccountTypeService',
    function($scope, $http, $location, EmployeeService, AccountTypeService) {

        $scope.passwordKey = "3e47327f62b74ec718f2c31b30f6ff0d7441f9ae143622173b067ac8d531f6e64c80a50eb497050bf6485f4270e5755904938e667e9a0ab5b2a75301a974648e";
        $scope.employee = {};
        $scope.accountTypes = {};
        $scope.selectedAccountTypeId = 0;

        $scope.requiredErrorMessage = 'Please fill out this field';

        $scope.reset = function() {
            $scope.employee= {};
        };

        $scope.encrypt = function(password) {
            var result = "";

            for (var i = 0; i < password.length; ++i) {
                result += String.fromCharCode($scope.passwordKey[i % $scope.passwordKey.length] ^ password.charCodeAt(i));
            }

            return result;
        };

        AccountTypeService.findAllAccountTypees().then(function(res) {
            $scope.accountTypes = res.data;
        }, function(err) {
            console.log('An error occurred while finding all account types: ' + err.status);
        });

        $scope.updateEmployeeAccountType = function() {
            if ($scope.selectedAccountTypeId !== 0) {
                AccountTypeService.findAccountType($scope.selectedAccountTypeId).then(function(res) {
                    $scope.employee.accountType = res.data;
                }, function(err) {
                    console.log('An error occurred while finding account type: ' + err.status);
                });
            } else {
                $scope.employee.accountType = {};
            }
        };

        $scope.save = function(employee) {
            if (!employee.password) {
                employee.password = $scope.encrypt(employee.firstName);
            }

            EmployeeService.addEmployee(employee).then(function(res) {
                $scope.employee = res.data;
                $location.url('/coordinator/employee/all');
            }, function(err) {
                console.log('An error occurred while adding employee: ' + err.status);
            });
        };

    }]);
