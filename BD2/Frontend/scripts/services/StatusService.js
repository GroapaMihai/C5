'use strict';

transportLogisticsApp.service('StatusService', ['$http', 'CommonResourcesFactory', function($http, CommonResourcesFactory) {
    return {
        findStatus: function (id) {
            return $http.get(CommonResourcesFactory.findStatusUrl + id)
                .success(function (data) {
                    return data;
                }).error(function (data) {
                    return data;
                });
        },
        findAllStatuses: function () {
            return $http.get(CommonResourcesFactory.findAllStatusesUrl)
                .success(function (data) {
                    return data;
                }).error(function (data) {
                    return data;
                });
        }
    }
}]);