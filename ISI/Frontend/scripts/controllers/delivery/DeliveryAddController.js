'use strict';

transportLogisticsApp.controller('DeliveryAddController', ['$scope', '$http', '$location', 'DeliveryService', 'CountryService', 'AddressService', 'PriorityService',
    function($scope, $http, $location, DeliveryService, CountryService, AddressService, PriorityService) {

        $scope.delivery = {};
        $scope.countries = {};
        $scope.selectedStartingCountryName = "";
        $scope.selectedDestinationCountryName = "";
        $scope.selectedStartingAddressId = 0;
        $scope.selectedDestinationAddressId = 0;
        $scope.startingAddresses = {};
        $scope.destinationAddresses = {};
        $scope.priorities = {};
        $scope.selectedPriorityId = 0;

        $scope.requiredErrorMessage = 'Please fill out this field';

        $scope.reset = function() {
            $scope.delivery= {};
        };

        CountryService.findAllCountries().then(function(res) {
            $scope.countries = res.data;
        }, function(err) {
            console.log('An error occurred while finding all countries: ' + err.status);
        });

        PriorityService.findAllPriorities().then(function(res) {
            $scope.priorities = res.data;
        }, function(err) {
            console.log('An error occurred while finding all priorities: ' + err.status);
        });

        $scope.updateStartingAddressesList = function() {
            if ($scope.selectedStartingCountryName !== "") {
                AddressService.findAddressesByCountryName($scope.selectedStartingCountryName).then(function(res) {
                    $scope.startingAddresses = res.data;
                }, function(err) {
                    console.log('An error occurred while finding all starting addresses: ' + err.status);
                });
            } else {
                $scope.delivery.startingAddress = {};
                $scope.selectedStartingAddressId = 0;
            }
        };

        $scope.updateDestinationAddressesList = function() {
            if ($scope.selectedDestinationCountryName !== "") {
                AddressService.findAddressesByCountryName($scope.selectedDestinationCountryName).then(function(res) {
                    $scope.destinationAddresses = res.data;
                }, function(err) {
                    console.log('An error occurred while finding all destination addresses: ' + err.status);
                });
            } else {
                $scope.delivery.destinationAddress = {};
                $scope.selectedDestinationAddressId = 0;
            }
        };

        $scope.updateStartingAddress = function() {
            if ($scope.selectedStartingAddressId !== 0) {
                AddressService.findAddress($scope.selectedStartingAddressId).then(function(res) {
                    $scope.delivery.startingAddress = res.data;
                }, function(err) {
                    console.log('An error occurred while finding starting address: ' + err.status);
                });
            } else {
                $scope.delivery.startingAddress = {};
            }
        };

        $scope.updateDestinationAddress = function() {
            if ($scope.selectedDestinationAddressId !== 0) {
                AddressService.findAddress($scope.selectedDestinationAddressId).then(function(res) {
                    $scope.delivery.destinationAddress = res.data;
                }, function(err) {
                    console.log('An error occurred while finding destination address: ' + err.status);
                });
            } else {
                $scope.delivery.destinationAddress = {};
            }
        };

        $scope.updatePriority = function() {
            if ($scope.selectedPriorityId !== 0) {
                PriorityService.findPriority($scope.selectedPriorityId).then(function(res) {
                    $scope.delivery.priority = res.data;
                }, function(err) {
                    console.log('An error occurred while finding priority: ' + err.status);
                });
            } else {
                $scope.delivery.priority = {};
            }
        };

        $scope.save = function(delivery) {
            DeliveryService.addDelivery(delivery).then(function(res) {
                $scope.delivery = res.data;
                $location.url('/coordinator/delivery/all');
            }, function(err) {
                console.log('An error occurred while adding delivery: ' + err.status);
            });
        };

    }]);
