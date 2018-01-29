'use strict';

transportLogisticsApp.service('EmployeeService', ['$http', 'CommonResourcesFactory', function($http, CommonResourcesFactory) {
    return {
        addEmployee: function(employee) {
            return $http.post(CommonResourcesFactory.addEmployeeUrl, employee)
                .success(function(data) {
                    return data;
                })
                .error(function(data) {
                    return data;
                });
        },
        findEmployee: function(id) {
            return $http.get(CommonResourcesFactory.findEmployeeUrl + id)
                .success(function (data) {
                    return data;
                }).error(function (data) {
                    return data;
                });
        },
        findAllEmployees: function () {
            return $http.get(CommonResourcesFactory.findAllEmployeesUrl)
                .success(function (data) {
                    return data;
                }).error(function (data) {
                    return data;
                });
        },
        findEmployeesByAccountTypeName: function (accountTypeName) {
            return $http.get(CommonResourcesFactory.findEmployeesByAccountTypeNameUrl + accountTypeName)
                .success(function (data) {
                    return data;
                }).error(function (data) {
                    return data;
                });
        },
        findEmployeesByStatusName: function (statusName) {
            return $http.get(CommonResourcesFactory.findEmployeesByStatusNameUrl + statusName)
                .success(function (data) {
                    return data;
                }).error(function (data) {
                    return data;
                });
        },
        findEmployeesByAccountTypeNameAndStatusName: function (accountTypeName, statusName) {
            return $http.get(CommonResourcesFactory.findEmployeesByAccountTypeNameAndStatusNameUrl + accountTypeName + "/" + statusName)
                .success(function (data) {
                    return data;
                }).error(function (data) {
                    return data;
                });
        },
        findEmployeeByEmailAndPassword: function (email, password) {
            return $http.get(CommonResourcesFactory.findEmployeeByEmailAndPasswordUrl + email + "/" + password)
                .success(function (data) {
                    return data;
                }).error(function (data) {
                    return data;
                });
        },
        updateEmployee: function(employee) {
            return $http.put(CommonResourcesFactory.updateEmployeeUrl, employee)
                .success(function(data) {
                    return data;
                })
                .error(function(data) {
                    return data;
                });
        },
        deleteEmployee: function(id) {
            $http.delete(CommonResourcesFactory.deleteEmployeeUrl + id)
                .success(function(data) {
                    alert('Successfully deleted employee with id ' + id + "!");
                })
                .error(function(data) {
                    alert('Error while deleting employee with id ' + id + "!");
                });
        },
        findEligibleDrivers: function(revenueIncrease) {
            return $http.post(CommonResourcesFactory.findEligibleDriversUrl, revenueIncrease)
                .success(function(data) {
                    return data;
                })
                .error(function(data) {
                    return data;
                });
        },
        increaseDriversRevenue: function(revenueIncrease) {
            return $http.post(CommonResourcesFactory.increaseDriversRevenueUrl, revenueIncrease)
                .success(function(data) {
                    return data;
                })
                .error(function(data) {
                    return data;
                });
        }
    }
}]);