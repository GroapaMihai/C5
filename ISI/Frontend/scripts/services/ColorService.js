'use strict';

transportLogisticsApp.service('ColorService', ['$http', 'CommonResourcesFactory', function($http, CommonResourcesFactory) {
    return {
        findColor: function (id) {
            return $http.get(CommonResourcesFactory.findColorUrl + id)
                .success(function (data) {
                    return data;
                }).error(function (data) {
                    return data;
                });
        },
        findAllColors: function () {
            return $http.get(CommonResourcesFactory.findAllColorsUrl)
                .success(function (data) {
                    return data;
                }).error(function (data) {
                    return data;
                });
        }
    }
}]);