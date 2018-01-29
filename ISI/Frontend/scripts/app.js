var transportLogisticsApp = angular.module('transportLogisticsApp', ['ngRoute']);

transportLogisticsApp.config(['$routeProvider',
    function($routeProvider) {
        $routeProvider
            .when('/coordinator/delivery/add', {
                templateUrl: 'views/delivery/deliveryAdd.html',
                controller: 'DeliveryAddController'
            })
            .when('/coordinator/delivery/all', {
                templateUrl: 'views/delivery/deliveryList.html',
                controller: 'DeliveryListController'
            })
            .when('/coordinator/delivery/id/:id', {
                templateUrl: 'views/delivery/deliveryView.html',
                controller: 'DeliveryViewController'
            })
            .when('/coordinator/employee/add', {
                templateUrl: 'views/employee/employeeAdd.html',
                controller: 'EmployeeAddController'
            })
            .when('/coordinator/employee/id/:id', {
                templateUrl: 'views/employee/employeeView.html',
                controller: 'EmployeeViewController'
            })
            .when('/coordinator/employee/driversRevenueIncrease', {
                templateUrl: 'views/employee/driversRevenueIncrease.html',
                controller: 'DriversRevenueIncreaseController'
            })
            .when('/coordinator/employee/all', {
                templateUrl: 'views/employee/employeeList.html',
                controller: 'EmployeeListController'
            })
            .when('/coordinator/planification/add', {
                templateUrl: 'views/planification/planificationAdd.html',
                controller: 'PlanificationAddController'
            })
            .when('/coordinator/planification/all', {
                templateUrl: 'views/planification/coordinatorPlanificationList.html',
                controller: 'CoordinatorPlanificationListController'
            })
            .when('/coordinator/planification/id/:id', {
                templateUrl: 'views/planification/coordinatorPlanificationView.html',
                controller: 'CoordinatorPlanificationViewController'
            })
            .when('/driver/planification/id/:id', {
                templateUrl: 'views/planification/driverPlanificationView.html',
                controller: 'DriverPlanificationViewController'
            })
            .when('/driver/planification/all', {
                templateUrl: 'views/planification/driverPlanificationList.html',
                controller: 'DriverPlanificationListController'
            })
            .when('/coordinator/vehicle/add', {
                templateUrl: 'views/vehicle/vehicleAdd.html',
                controller: 'VehicleAddController'
            })
            .when('/coordinator/vehicle/id/:id', {
                templateUrl: 'views/vehicle/vehicleView.html',
                controller: 'VehicleViewController'
            })
            .when('/coordinator/vehicle/all', {
                templateUrl: 'views/vehicle/vehicleList.html',
                controller: 'VehicleListController'
            })
            .when('/coordinator/home', {
                templateUrl: 'views/coordinatorMainPage.html',
                controller: 'CoordinatorMainController'
            })
            .when('/driver/home', {
                templateUrl: 'views/driverMainPage.html',
                controller: 'DriverMainController'
            })
            .otherwise({
                templateUrl: 'views/login/login.html',
                controller: 'LoginController'
            });
    }]).run(['$rootScope',
    function($rootScope) {
    }
]);