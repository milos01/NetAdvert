(function(angular){
	app = angular.module('NetAdvertApp', ["ngRoute"]);
	app.config(	function($routeProvider, $httpProvider) {
		$httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
		$routeProvider
			.when('/', {
                templateUrl: 'core/views/login.html',
	            controller: 'navigation'
	      })
	      .when('/home', {
              templateUrl: 'core/views/home.html',
              controller: 'home'

	      })
	      .otherwise({
	        redirectTo: '/'
	      });
		});
	app.controller('navigation', function($rootScope, $scope, $http, $location){
		var authenticate = function(credentials, callback) {

            var headers = credentials ? {authorization : "Basic "
                + btoa(credentials.username + ":" + credentials.password)
                } : {};

            $http.get('http://localhost:8080/api/userr', {headers : headers}).then(function(response){
                if (response) {
                    $rootScope.authenticated = true;
                    $location.path("/home");
                } else {
                    $rootScope.authenticated = false;
                    console.log("not logged");
                }
                callback && callback();
            }, function() {
                $rootScope.authenticated = false;
                callback && callback();
            });
	  	}

		authenticate();

		$scope.credentials = {};
		$scope.login = function() {
            authenticate($scope.credentials, function() {

                if ($rootScope.authenticated) {
                    $location.path("/home");

                } else {
                    $location.path("/");

                }
            });
		};

	});

    app.controller('home', function($rootScope, $location){
        if(!$rootScope.authenticated){
            $location.path("/");
        }
    });
})(angular);