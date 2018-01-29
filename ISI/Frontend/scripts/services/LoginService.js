transportLogisticsApp.factory('LoginService', function() {
    var loggedInEmployee = {};

    return {
        set: set,
        get: get
    };

    function get() {
        return loggedInEmployee;
    }

    function set(employee){
        loggedInEmployee = employee;
    }
});