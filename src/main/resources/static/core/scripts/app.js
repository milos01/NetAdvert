(function(angular){
	app = angular.module('NetAdvertApp', ["ngRoute"]);
	app.config(	function($routeProvider) {

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

		    $http.get('http://localhost:8080/api/user').then(function (response) {
		      if (response.data) {
		          $rootScope.authenticated = true;
                  $location.path("/home");
		      } else {
				  console.log('nema ga');
                  $rootScope.authenticated = false;
		      }
		      callback && callback();
		    }, function () {
		      $rootScope.authenticated = false;
		      callback && callback();
		    });
	  	}

		authenticate();

		$scope.credentials = {};
		$scope.login = function() {
            console.log($scope.credentials.email);
            $http({
                method: 'POST',
                url: 'api/login',
                headers : {
                    'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'
                },
                data: $.param({email: $scope.credentials.email, password: $scope.credentials.password})
            }).then(function successCallback(response) {
                $location.path("/home");
            }, function errorCallback(response) {
                alert('ne valja');
            });
		};

	});

    app.controller('home', function($rootScope, $location){
        if(!$rootScope.authenticated){
            $location.path("/");
        }
    });
})(angular);