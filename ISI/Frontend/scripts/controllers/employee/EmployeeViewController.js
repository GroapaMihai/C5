'use strict';

transportLogisticsApp.controller('EmployeeViewController', ['$scope', '$http', '$routeParams', '$location', 'EmployeeService',
    function($scope, $http, $routeParams, $location, EmployeeService) {

        EmployeeService.findEmployee($routeParams.id)
            .then(function(res) {
                $scope.employee = res.data;
            }, function(err) {
                console.log('An error occurred while finding the employee: ' + err.status);
            });

        $scope.back = function() {
            $location.url('/coordinator/employee/all');
        };

    }]);