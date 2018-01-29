'use strict';

transportLogisticsApp.controller('CoordinatorMenuController', ['$scope', '$interval', 'DeliveryActionsService', 'EmployeeActionsService', 'CoordinatorPlanificationActionsService', 'VehicleActionsService', 'LoginService', 'LoggedInEmployeeActionsService',
    function($scope, $interval, DeliveryActionsService, EmployeeActionsService, CoordinatorPlanificationActionsService, VehicleActionsService, LoginService, LoggedInEmployeeActionsService) {

        $scope.deliveryActionsList = DeliveryActionsService;
        $scope.employeeActionsList = EmployeeActionsService;
        $scope.planficationActionsList = CoordinatorPlanificationActionsService;
        $scope.vehicleActionsList = VehicleActionsService;
        $scope.loggedInEmployeeActionsList = LoggedInEmployeeActionsService;
        $scope.loggedInEmployee = LoginService.get();

        $interval(function() {
            $scope.currentDateTime = new Date();
        }, 1000);

    }]);