'use strict';

transportLogisticsApp.service('AddressService', ['$http', 'CommonResourcesFactory', function($http, CommonResourcesFactory) {
    return {
        findAddress: function (id) {
            return $http.get(CommonResourcesFactory.findAddressUrl + id)
                .success(function (data) {
                    return data;
                }).error(function (data) {
                    return data;
                });
        },
        findAllAddresses: function () {
            return $http.get(CommonResourcesFactory.findAllAddressesUrl)
                .success(function (data) {
                    return data;
                }).error(function (data) {
                    return data;
                });
        },
        findAddressesByCountryName: function (countryName) {
            return $http.get(CommonResourcesFactory.findAddressesByCountryNameUrl + countryName)
                .success(function (data) {
                    return data;
                }).error(function (data) {
                    return data;
                });
        }
    }
}]);