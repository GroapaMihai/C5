transportLogisticsApp.controller('DriverPlanificationListController', ['$scope', '$http', '$route', '$location', 'PlanificationService', 'StatusService', 'LoginService',
    function($scope, $http, $route, $location, PlanificationService, StatusService, LoginService) {

        $scope.selectedStatus = new Object();
        $scope.selectedStatus.name = "All";

        $scope.loggedInEmployee = LoginService.get();
        $scope.statuses = [];
        $scope.planifications = [];

        StatusService.findAllStatuses().then(function(res) {
            $scope.statuses = res.data;
            $scope.updatePlanificationList();
        }, function(err) {
            console.log('An error occurred while finding all statuses: ' + err.status);
        });

        $scope.updatePlanificationList = function() {
            PlanificationService.findPlanificationsByDriverAndStatusName($scope.loggedInEmployee.id, $scope.selectedStatus.name).then(function(res) {
                $scope.planifications = res.data;
            }, function(err) {
                console.log('An error occurred while finding all driver\'s planifications: ' + err.status);
            });
        };

        $scope.view = function(id) {
            $location.url('/driver/planification/id/' + id);
        };
    }]);