'use strict';

transportLogisticsApp.controller('CoordinatorMainController', ['$scope', function($scope) {

    $scope.title = '[Coordinator] Transport Logistics Application';
    $scope.description = "Web application for transport logistics";

    $scope.descriptionShow = false;
    $scope.toggleDescription= function() {
        $scope.descriptionShow = !$scope.descriptionShow;
    };

}]);