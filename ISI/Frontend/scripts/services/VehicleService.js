'use strict';

transportLogisticsApp.service('VehicleService', ['$http', 'CommonResourcesFactory', function($http, CommonResourcesFactory) {
    return {
        addVehicle: function(vehicle) {
            return $http.post(CommonResourcesFactory.addVehicleUrl, vehicle)
                .success(function(data) {
                    return data;
                })
                .error(function(data) {
                    return data;
                });
        },
        findVehicle: function(id) {
            return $http.get(CommonResourcesFactory.findVehicleUrl + id)
                .success(function (data) {
                    return data;
                }).error(function (data) {
                    return data;
                });
        },
        findAllVehicles: function () {
            return $http.get(CommonResourcesFactory.findAllVehiclesUrl)
                .success(function (data) {
                    return data;
                }).error(function (data) {
                    return data;
                });
        },
        findVehiclesByStatusName: function (statusName) {
            return $http.get(CommonResourcesFactory.findVehiclesByStatusNameUrl + statusName)
                .success(function (data) {
                    return data;
                }).error(function (data) {
                    return data;
                });
        },
        updateVehicle: function(vehicle) {
            return $http.put(CommonResourcesFactory.updateVehicleUrl, vehicle)
                .success(function(data) {
                    return data;
                })
                .error(function(data) {
                    return data;
                });
        },
        deleteVehicle: function(id) {
            $http.delete(CommonResourcesFactory.deleteVehicleUrl + id)
                .success(function(data) {
                    alert('Successfully deleted vehicle with id ' + id + "!");
                })
                .error(function(data) {
                    alert('Error while deleting vehicle with id ' + id + "!");
                });
        }
    }
}]);