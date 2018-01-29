'use strict';

transportLogisticsApp.service('PriorityService', ['$http', 'CommonResourcesFactory', function($http, CommonResourcesFactory) {
    return {
        findPriority: function (id) {
            return $http.get(CommonResourcesFactory.findPriorityUrl + id)
                .success(function (data) {
                    return data;
                }).error(function (data) {
                    return data;
                });
        },
        findAllPriorities: function () {
            return $http.get(CommonResourcesFactory.findAllPrioritiesUrl)
                .success(function (data) {
                    return data;
                }).error(function (data) {
                    return data;
                });
        }
    }
}]);