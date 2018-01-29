'use strict';

transportLogisticsApp.controller('DriverMainController', ['$scope', function($scope) {

    $scope.title = '[Driver] Transport Logistics Application';
    $scope.description = "Web application for transport logistics";

    $scope.descriptionShow = false;
    $scope.toggleDescription= function() {
        $scope.descriptionShow = !$scope.descriptionShow;
    };

}]);