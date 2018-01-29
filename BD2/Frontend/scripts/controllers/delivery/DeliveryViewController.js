'use strict';

transportLogisticsApp.controller('DeliveryViewController', ['$scope', '$http', '$routeParams', '$location', 'DeliveryService',
    function($scope, $http, $routeParams, $location, DeliveryService) {

        DeliveryService.findDelivery($routeParams.id)
            .then(function(res) {
                $scope.delivery = res.data;
            }, function(err) {
                console.log('An error occurred while finding the delivery: ' + err.status);
            });

        $scope.back = function() {
            $location.url('/coordinator/delivery/all');
        };

    }]);