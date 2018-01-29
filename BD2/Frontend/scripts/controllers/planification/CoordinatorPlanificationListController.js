transportLogisticsApp.controller('CoordinatorPlanificationListController', ['$scope', '$http', '$route', '$location', 'PlanificationService', 'StatusService',
    function($scope, $http, $route, $location, PlanificationService, StatusService) {

        $scope.selectedStatus = new Object();
        $scope.selectedStatus.name = "All";

        StatusService.findAllStatuses().then(function(res) {
            $scope.statuses = res.data;
            $scope.updatePlanificationList();
        }, function(err) {
            console.log('An error occurred while finding all statuses: ' + err.status);
        });

        $scope.updatePlanificationList = function() {
            PlanificationService.findPlanificationsByStatusName($scope.selectedStatus.name).then(function(res) {
                $scope.planifications = res.data;
            }, function(err) {
                console.log('An error occurred while finding all planifications: ' + err.status);
            });
        };

        $scope.view = function(id) {
            $location.url('/coordinator/planification/id/' + id);
        };
    }]);