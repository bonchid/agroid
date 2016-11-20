
var userId = 0;
var userGroup = '';

var doaApp = angular.module('doaApp', ['ngRoute'])
    .config(function ($routeProvider) {
        $routeProvider
            .when('/preffix', {
                templateUrl: 'preffix.html',
                controller: 'preffixCtrl'
            })
            .when('/login', {
                templateUrl: 'login.html',
                controller: 'loginCtrl'
            })
            .when('/distributor', {
                templateUrl: 'distributor.html',
                controller: 'distributorCtrl'
            })
            .otherwise({
                redirectTo: '/login'
            });
    });

doaApp.controller('MainController', function ($scope, $http, $location) {
    if (userId == 0) $location.path('login');
});

doaApp.controller('distributorCtrl', function ($scope, $http, $location) {
    if (userId == 0) $location.path('login');

});


doaApp.controller('loginCtrl', function ($scope, $location) {

    $scope.auth = function () {
        $.post('/auth/getUserId', {login: $scope.login, password: $scope.password}, function (data, status) {
            if (status == 'success'){
                userId = data;
                getUserGroup(userId);

                if (userGroup == 'admin') $location.path('preffix');
                if (userGroup == 'user') $location.path('distributor');
            }
        })
    };

    function getUserGroup(id) {
        $.post('/auth/getUserGroup', {id: id}, function (data, status) {
            if (status == 'success'){
                userGroup = data;
            }
        })
    }

});


doaApp.controller('preffixCtrl', function ($scope, $http, $location) {

    if (userId == 0) $location.path('login');

    getPreffixes();
    getUsers();

    function getPreffixes() {
        $http.post('/get/preffixList', {}).success(function (data) {
            $scope.preffixList = data;
        })
    }

    function getUsers() {
        $http.post('/get/userList', {}).success(function (data) {
            $scope.userList = data;
        })
    }


    $scope.addPreffix = function () {



        $.post('/set/newPreffix', {
            registry: $scope.registry,
            registrant: $scope.registrant,
            name: $scope.name,
            address: $scope.address,
            userId: $scope.userId.id
        }, function (data, status) {
            if (status == 'success') getPreffixes();
        })
    }

});
