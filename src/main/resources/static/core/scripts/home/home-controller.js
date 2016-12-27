/**
 * Created by milosandric on 23/12/2016.
 */
(function(angular){
    app.controller('home', function ($rootScope, $location, $scope, _, $log, UsersResource) {
        if(!$rootScope.authenticated){
            $location.path("/");
        }

        UsersResource.getStudents().then(function (items) {
            $scope.students = items;
        })

    })
})(angular);