transportLogisticsApp.controller('VehicleListController', ['$scope', '$http', '$route', '$location', 'VehicleService', 'StatusService',
    function($scope, $http, $route, $location, VehicleService, StatusService) {

        $scope.statuses = {};
        $scope.selectedStatusName = "All";

        StatusService.findAllStatuses().then(function(res) {
            $scope.statuses = res.data;
            $scope.updateVehicleList();
        }, function(err) {
            console.log('An error occurred while finding all statuses: ' + err.status);
        });

        $scope.updateVehicleList = function() {
            VehicleService.findVehiclesByStatusName($scope.selectedStatusName).then(function(res) {
                $scope.vehicles = res.data;
            }, function(err) {
                console.log('An error occurred while finding all vehicles: ' + err.status);
            });
        };

        $scope.view = function(vehicleId) {
            $location.url('/coordinator/vehicle/id/' + vehicleId);
        };
    }]);