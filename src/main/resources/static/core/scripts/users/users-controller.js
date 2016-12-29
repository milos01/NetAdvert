/**
 * Created by milosandric on 29/12/2016.
 */
(function(angular){
    app.controller('profile', function ($rootScope, $location, $scope, _, $log, UsersResource, AdvertResource, PictureResource) {
        if(!$rootScope.authenticated){
            $location.path("/");
        }
    })
})(angular);