'use strict';

transportLogisticsApp.service('PlanificationService', ['$http', 'CommonResourcesFactory', function($http, CommonResourcesFactory) {
    return {
        addPlanification: function(planification) {
            return $http.post(CommonResourcesFactory.addPlanificationUrl, planification)
                .success(function(data) {
                    return data;
                })
                .error(function(data) {
                    return data;
                });
        },
        findPlanification: function(id) {
            return $http.get(CommonResourcesFactory.findPlanificationUrl + id)
                .success(function (data) {
                    return data;
                }).error(function (data) {
                    return data;
                });
        },
        findAllPlanifications: function () {
            return $http.get(CommonResourcesFactory.findAllPlanificationsUrl)
                .success(function (data) {
                    return data;
                }).error(function (data) {
                    return data;
                });
        },
        findPlanificationsByDriver: function(id) {
            return $http.get(CommonResourcesFactory.findPlanificationsByDriverUrl + id)
                .success(function (data) {
                    return data;
                }).error(function (data) {
                    return data;
                });
        },
        findPlanificationsByTruck: function(id) {
            return $http.get(CommonResourcesFactory.findPlanificationsByTruckUrl + id)
                .success(function (data) {
                    return data;
                }).error(function (data) {
                    return data;
                });
        },
        findPlanificationsByStatusName: function(statusName) {
            return $http.get(CommonResourcesFactory.findPlanificationsByStatusNameUrl + statusName)
                .success(function (data) {
                    return data;
                }).error(function (data) {
                    return data;
                });
        },
        findPlanificationsByDriverAndStatusName: function(driverId, statusName) {
            return $http.get(CommonResourcesFactory.findPlanificationsByDriverAndStatusNameUrl + driverId + "/" + statusName)
                .success(function (data) {
                    return data;
                }).error(function (data) {
                    return data;
                });
        },
        findPlanificationsByTruckAndStatusName: function(truckId, statusName) {
            return $http.get(CommonResourcesFactory.findPlanificationsByTruckAndStatusNameUrl + truckId + "/" + statusName)
                .success(function (data) {
                    return data;
                }).error(function (data) {
                    return data;
                });
        },
        updatePlanification: function(planification) {
            return $http.put(CommonResourcesFactory.updatePlanificationUrl, planification)
                .success(function(data) {
                    return data;
                })
                .error(function(data) {
                    return data;
                });
        },
        deletePlanification: function(id) {
            $http.delete(CommonResourcesFactory.deletePlanificationUrl + id)
                .success(function(data) {
                    alert('Successfully deleted planification with id ' + id + "!");
                })
                .error(function(data) {
                    alert('Error while deleting planification with id ' + id + "!");
                });
        },
        deliverPlanification: function(planification) {
            return $http.post(CommonResourcesFactory.deliverPlanificationUrl, planification)
                .success(function(data) {
                    return data;
                })
                .error(function(data) {
                    return data;
                });
        },
    }
}]);
