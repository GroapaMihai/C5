'use strict';

transportLogisticsApp.factory('CommonResourcesFactory', function() {
        var baseUrl = "http://localhost:9090/";
        return {
            findAccountTypeUrl : baseUrl + "accountType/id/",
            findAllAccountTypesUrl: baseUrl + "accountType/all",

            findAddressUrl : baseUrl + "address/id/",
            findAllAddressesUrl: baseUrl + "address/all",
            findAddressesByCountryNameUrl: baseUrl + "address/country/",

            findColorUrl : baseUrl + "color/id/",
            findAllColorsUrl: baseUrl + "color/all",

            findCountryUrl : baseUrl + "country/id/",
            findAllCountriesUrl: baseUrl + "country/all",

            addDeliveryUrl : baseUrl + "delivery/add",
            findDeliveryUrl : baseUrl + "delivery/id/",
            findAllDeliveriesUrl : baseUrl + "delivery/all",
            findDeliveriesByStatusNameUrl : baseUrl + "delivery/status/",
            updateDeliveryUrl : baseUrl + "delivery/update/id/",
            deleteDeliveryUrl : baseUrl + "delivery/delete/id/",

            addEmployeeUrl : baseUrl + "employee/add",
            findEmployeeUrl : baseUrl + "employee/id/",
            findAllEmployeesUrl : baseUrl + "employee/all",
            findEmployeesByAccountTypeNameUrl : baseUrl + "employee/accountType/",
            findEmployeesByStatusNameUrl : baseUrl + "employee/status/",
            findEmployeesByAccountTypeNameAndStatusNameUrl : baseUrl + "employee/accountType/status/",
            findEmployeeByEmailAndPasswordUrl : baseUrl + "employee/email/password/",
            updateEmployeeUrl : baseUrl + "employee/update/id/",
            deleteEmployeeUrl : baseUrl + "employee/delete/id/",
            findEligibleDriversUrl: baseUrl + "employee/eligibleDrivers/",
            increaseDriversRevenueUrl: baseUrl + "employee/driversRevenueIncrease/",

            addPlanificationUrl : baseUrl + "planification/add",
            findPlanificationUrl : baseUrl + "planification/id/",
            findAllPlanificationsUrl: baseUrl + "planification/all",
            findPlanificationsByDriverUrl: baseUrl + "planification/driver/",
            findPlanificationsByTruckUrl: baseUrl + "planification/truck/",
            findPlanificationsByStatusNameUrl: baseUrl + "planification/status/",
            findPlanificationsByDriverAndStatusNameUrl: baseUrl + "planification/driver/status/",
            findPlanificationsByTruckAndStatusNameUrl: baseUrl + "planification/truck/status/",
            updatePlanificationUrl : baseUrl + "planification/update/id/",
            deletePlanificationUrl : baseUrl + "planification/delete/id/",
            deliverPlanificationUrl: baseUrl + "planification/deliver",

            findPriorityUrl : baseUrl + "priority/id/",
            findAllPrioritiesUrl: baseUrl + "priority/all",

            findStatusUrl : baseUrl + "status/id/",
            findAllStatusesUrl: baseUrl + "status/all",
            findStatusByStatusNameUrl: baseUrl + "status/statusName/",

            addVehicleUrl : baseUrl + "vehicle/add",
            findVehicleUrl : baseUrl + "vehicle/id/",
            findAllVehiclesUrl : baseUrl + "vehicle/all",
            findVehiclesByStatusNameUrl : baseUrl + "vehicle/status/",
            updateVehicleUrl : baseUrl + "vehicle/update/id/",
            deleteVehicleUrl : baseUrl + "vehicle/delete/id/"
        };
    }
);