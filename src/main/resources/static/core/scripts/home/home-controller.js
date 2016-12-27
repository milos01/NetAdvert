/**
 * Created by milosandric on 23/12/2016.
 */
(function(angular){
    app.controller('home', function ($rootScope, $location, $scope, _, $log, UsersResource, AdvertResource, PictureResource) {
        if(!$rootScope.authenticated){
            $location.path("/");
        }

        UsersResource.getStudents().then(function (items) {
            $scope.students = items;
        })

        AdvertResource.getAdverts().then(function (items) {
            angular.forEach(items, function(value, key) {
                PictureResource.getAdvertMainPicture(value.realestate.id).then(function (item) {
                    value.main_pic = item.pictureName;
                })
            });
            $scope.adverts = items;
        })
    })
})(angular);