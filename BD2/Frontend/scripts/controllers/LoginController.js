'use strict';

transportLogisticsApp.controller('LoginController', ['$scope', '$location', '$window', 'EmployeeService', 'LoginService',
    function($scope, $location, $window, EmployeeService, LoginService) {

    $scope.loginFailureMessage = "Login failed!";
    $scope.loginSuccessMessage = "Login success!";
    $scope.passwordKey = "3e47327f62b74ec718f2c31b30f6ff0d7441f9ae143622173b067ac8d531f6e64c80a50eb497050bf6485f4270e5755904938e667e9a0ab5b2a75301a974648e";
    $scope.email = "";
    $scope.password = "";
    $scope.employee = {};

    $scope.requiredErrorMessage = 'Please fill out this field';

    $scope.encrypt = function() {
        var result = "";

        for (var i = 0; i < $scope.password.length; ++i) {
            result += String.fromCharCode($scope.passwordKey[i % $scope.passwordKey.length] ^ $scope.password.charCodeAt(i));
        }

        return result;
    };

    $scope.login = function() {
        var encryptedPassword = $scope.encrypt();

        EmployeeService.findEmployeeByEmailAndPassword($scope.email, encryptedPassword).then(function(res) {
            $scope.employee = res.data;
            LoginService.set($scope.employee);

            if (!$scope.employee) {
                $window.alert($scope.loginFailureMessage);
            } else if ($scope.employee.accountType.name === "Coordinator") {
                $window.alert($scope.loginSuccessMessage);
                $location.url('/coordinator/home');
            } else if ($scope.employee.accountType.name === "Driver") {
                $window.alert($scope.loginSuccessMessage);
                $location.url('/driver/home');
            }
        }, function(err) {
            console.log('An error occurred while logging in: ' + err.status);
        });
    };

}]);