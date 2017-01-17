(function(angular){
	app = angular.module('NetAdvertApp', ['ngResource',
        'ui.router',
        'restangular',
        'ui.bootstrap',
        'lodash',
        'ngAnimate']);
	app.config(	function($stateProvider, $urlRouterProvider, $httpProvider) {
		$httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
        $urlRouterProvider.otherwise('/');
        $stateProvider
            .state('login', {
                url: "/",
                views: {
                    'mainView@': {
                        templateUrl: "core/views/home.html",
                        controller: 'home'
                    }
                }

            })
            .state('profile', {
                url: "/profile",
                views: {
                    'mainView@': {
                        templateUrl: "core/views/profile.html",
                        controller: 'profile'
                    }
                }
            })
            .state('company', {
                url: "/company",
                views: {
                    'mainView@': {
                        templateUrl: "core/views/company.html",
                        controller: 'company'
                    }
                }
            })
            .state('mycompany', {
                url: "/mycompany",
                views: {
                    'mainView@': {
                        templateUrl: "core/views/mycompany.html",
                        controller: 'mycompany'
                    }
                }
            })
            .state('advert', {
                url: "/advert/:advertId",
                views: {
                    'mainView@': {
                        templateUrl: "core/views/advert.html",
                        controller: 'advert'
                    }
                }
            })
            .state('creds', {
                parent: 'profile',
                views: {
                    'profileView@profile': {
                        templateUrl: "core/views/creds.html",
                        controller: "usercred"
                    }
                }

            })
            .state('creds2', {
                parent: 'profile',
                views: {
                    'profileView@profile': {
                        templateUrl: "core/views/creds2.html"
                    }
                }

            })
            .state('report', {
            	url: "/report",
                views: {
                    'mainView@': {
                        templateUrl: "core/views/reports.html",
                        controller: "reportV"
                    }
                }

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



	app.controller('navigation', function($rootScope, $scope, $http, $location, CompanyResource){
		var authenticate = function(credentials, callback) {

            var headers = credentials ? {authorization : "Basic "
                + btoa(credentials.username + ":" + credentials.password)
                } : {};

            $http.get('http://localhost:8080/api/userr', {headers : headers}).then(function(response){
                console.log(response.data);
                var role = response.data.authorities[0].authority;
                console.log(role);
                if (response) {
                    $http.get('http://localhost:8080/api/getuser', {params: {email : response.data.name}}).then(function(response){
                        CompanyResource.checkIfUserMainOnCompany(response.data.id).then(function (res) {
                            $rootScope.user = {
                                uid: response.data.id,
                                fname :response.data.first_name,
                                lname :response.data.last_name,
                                email :response.data.email,
                                role: role,
                                isMain:res
                            }
                            socket = io('http://localhost:3000');
                            socket.emit('userLoad', $rootScope.user);
                        });
                    });

                    $rootScope.authenticated = true;
                    $location.path("/");
                } else {
                    $rootScope.user = {}
                    $rootScope.authenticated = false;
                    console.log("not logged");
                }
                callback && callback();
            }, function() {
                $rootScope.user = {}
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
		
		$scope.logout = function () {
            $http.post('logout', {}).finally(function() {
                $rootScope.authenticated = false;
                $location.path("/");
            });
        }

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

    app.controller('PagerDemoCtrl', function($scope) {
        $scope.totalItems = 64;
        $scope.currentPage = 4;
    });

})(angular);