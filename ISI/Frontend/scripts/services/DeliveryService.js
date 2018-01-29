'use strict';

transportLogisticsApp.service('DeliveryService', ['$http', 'CommonResourcesFactory', function($http, CommonResourcesFactory) {
    return {
        addDelivery: function(delivery) {
            return $http.post(CommonResourcesFactory.addDeliveryUrl, delivery)
                .success(function(data) {
                    return data;
                })
                .error(function(data) {
                    return data;
                });
        },
        findDelivery: function(id) {
            return $http.get(CommonResourcesFactory.findDeliveryUrl + id)
                .success(function (data) {
                    return data;
                }).error(function (data) {
                    return data;
                });
        },
        findAllDeliveries: function () {
            return $http.get(CommonResourcesFactory.findAllDeliveriesUrl)
                .success(function (data) {
                    return data;
                }).error(function (data) {
                    return data;
                });
        },
        findDeliveriesByStatusName: function (statusName) {
            return $http.get(CommonResourcesFactory.findDeliveriesByStatusNameUrl + statusName)
                .success(function (data) {
                    return data;
                }).error(function (data) {
                    return data;
                });
        },
        updateDelivery: function(delivery) {
            return $http.put(CommonResourcesFactory.updateDeliveryUrl, delivery)
                .success(function(data) {
                    return data;
                })
                .error(function(data) {
                    return data;
                });
        },
        deleteDelivery: function(id) {
            $http.delete(CommonResourcesFactory.deleteDeliveryUrl + id)
                .success(function(data) {
                    alert('Successfully deleted delivery with id ' + id + "!");
                })
                .error(function(data) {
                    alert('Error while deleting delivery with id ' + id + "!");
                });
        }
    }
}]);