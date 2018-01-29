'use strict';

transportLogisticsApp.service('CountryService', ['$http', 'CommonResourcesFactory', function($http, CommonResourcesFactory) {
    return {
        findCountry: function (id) {
            return $http.get(CommonResourcesFactory.findCountryUrl + id)
                .success(function (data) {
                    return data;
                }).error(function (data) {
                    return data;
                });
        },
        findAllCountries: function () {
            return $http.get(CommonResourcesFactory.findAllCountriesUrl)
                .success(function (data) {
                    return data;
                }).error(function (data) {
                    return data;
                });
        }
    }
}]);