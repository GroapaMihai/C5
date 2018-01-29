'use strict';

transportLogisticsApp.value('DeliveryActionsService', [
    {
        label: "Add delivery",
        url: "#/coordinator/delivery/add"
    },
    {
        label: "List deliveries",
        url: "#/coordinator/delivery/all"
    }
]);

transportLogisticsApp.value('EmployeeActionsService', [
    {
        label: "Add employee",
        url: "#/coordinator/employee/add"
    },
    {
        label: "List employees",
        url: "#/coordinator/employee/all"
    },
    {
        label: "Drivers revenue increase",
        url: "#/coordinator/employee/driversRevenueIncrease"
    }
]);

transportLogisticsApp.value('CoordinatorPlanificationActionsService', [
    {
        label: "Add planification",
        url: "#/coordinator/planification/add"
    },
    {
        label: "List planifications",
        url: "#/coordinator/planification/all"
    }
]);

transportLogisticsApp.value('DriverPlanificationActionsService', [
    {
        label: "List my planifications",
        url: "#/driver/planification/all"
    }
]);

transportLogisticsApp.value('VehicleActionsService', [
    {
        label: "Add vehicle",
        url: "#/coordinator/vehicle/add"
    },
    {
        label: "List vehicles",
        url: "#/coordinator/vehicle/all"
    }
]);

transportLogisticsApp.value('LoggedInEmployeeActionsService', [
    {
        label: "Logout",
        url: "#"
    }
]);
