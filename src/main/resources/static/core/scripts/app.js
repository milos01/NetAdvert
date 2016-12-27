(function(angular){
	app = angular.module('NetAdvertApp', ['ngResource',
        'ui.router',
        'restangular',
        'ui.bootstrap',
        'lodash',
        'ngAnimate']);
	app.config(	function($stateProvider, $httpProvider) {
		$httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
        $stateProvider
            .state('login', {
                url: "/",
                templateUrl: "core/views/home.html"
                // controller: 'navigation'
            })
            .state('test', {
                url: "/test",
                templateUrl: "core/views/main.html"
            })


    })
    .run(function(Restangular, $log, $rootScope, $state) {
        $rootScope.state = $state;

        Restangular.setBaseUrl("api");
        Restangular.setErrorInterceptor(function(response) {
            if (response.status === 500) {
                $log.info("internal server error");
                return true;
            }
            return true; // greska nije obradjena
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
                    $location.path("/");
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


                } else {
                    $location.path("/");

                }
            });
		};

	});

	app.controller('testCtr', function ($scope) {
        $scope.testfx = function(){
            alert('radi');
        }
    })
    app.directive('myCustomer', function($rootScope, $log) {
        return {
            link: function (rootScope, scope) {
                rootScope.$watch('authenticated', function(newval, oldval) {
                    $log.info(newval + " " + oldval);

                    if(newval){
                        $rootScope.testt = 'u';
                    }else{
                        $rootScope.testt = 'u+';
                    }
                })
            },
            template: '<div>{{testt}}</div>'
        };
    });


})(angular);