transportLogisticsApp.controller('EmployeeListController', ['$scope', '$http', '$route', '$location', 'EmployeeService', 'StatusService',
    function($scope, $http, $route, $location, EmployeeService, StatusService) {

        $scope.selectedStatusName = "All";

        StatusService.findAllStatuses().then(function(res) {
            $scope.statuses = res.data;
            $scope.updateEmployeeList();
        }, function(err) {
            console.log('An error occurred while finding all statuses: ' + err.status);
        });

        $scope.updateEmployeeList = function() {
            EmployeeService.findEmployeesByStatusName($scope.selectedStatusName).then(function(res) {
                $scope.employees = res.data;
            }, function(err) {
                console.log('An error occurred while finding all employees: ' + err.status);
            });
        };

        $scope.view = function(id) {
            $location.url('/coordinator/employee/id/' + id);
        };
    }]);