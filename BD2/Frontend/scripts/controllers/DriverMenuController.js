'use strict';

transportLogisticsApp.controller('DriverMenuController', ['$scope', '$interval', 'DriverPlanificationActionsService', 'LoginService', 'LoggedInEmployeeActionsService',
    function($scope, $interval, DriverPlanificationActionsService, LoginService, LoggedInEmployeeActionsService) {

        $scope.planficationActionsList = DriverPlanificationActionsService;
        $scope.loggedInEmployeeActionsList = LoggedInEmployeeActionsService;
        $scope.loggedInEmployee = LoginService.get();

        $interval(function() {
            $scope.currentDateTime = new Date();
        }, 1000);

    }]);