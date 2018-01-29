'use strict';

transportLogisticsApp.service('AccountTypeService', ['$http', 'CommonResourcesFactory', function($http, CommonResourcesFactory) {
    return {
        findAccountType: function (id) {
            return $http.get(CommonResourcesFactory.findAccountTypeUrl + id)
                .success(function (data) {
                    return data;
                }).error(function (data) {
                    return data;
                });
        },
        findAllAccountTypees: function () {
            return $http.get(CommonResourcesFactory.findAllAccountTypesUrl)
                .success(function (data) {
                    return data;
                }).error(function (data) {
                    return data;
                });
        }
    }
}]);