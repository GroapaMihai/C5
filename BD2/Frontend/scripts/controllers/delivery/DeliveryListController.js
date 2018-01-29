transportLogisticsApp.controller('DeliveryListController', ['$scope', '$http', '$route', '$location', 'DeliveryService', 'StatusService',
    function($scope, $http, $route, $location, DeliveryService, StatusService) {

        $scope.selectedStatusName = "All";

        StatusService.findAllStatuses().then(function(res) {
            $scope.statuses = res.data;
            $scope.updateDeliveryList();
        }, function(err) {
            console.log('An error occurred while finding all statuses: ' + err.status);
        });

        $scope.updateDeliveryList = function() {
            DeliveryService.findDeliveriesByStatusName($scope.selectedStatusName).then(function(res) {
                $scope.deliveries = res.data;
            }, function(err) {
                console.log('An error occurred while finding all deliveries: ' + err.status);
            });
        };

        $scope.view = function(id) {
            $location.url('/coordinator/delivery/id/' + id);
        };
    }]);