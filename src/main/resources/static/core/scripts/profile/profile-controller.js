/**
 * Created by milosandric on 28/12/2016.
 */
(function(angular){
    app.controller('usercred', function ($rootScope, $location, $scope, _, $log, UsersResource) {
        $scope.updateLname = $scope.user.lname;
        $scope.updateFname = $scope.user.fname;
        $scope.updateUserInfo = function(){
            $scope.user.lname = $scope.updateLname;
            $scope.user.fname = $scope.updateFname;
            UsersResource.updateUser($scope.user.email, $scope.user.fname, $scope.user.lname);
        }
    })
})(angular);