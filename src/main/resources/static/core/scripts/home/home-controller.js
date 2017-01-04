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

        $scope.loadNewPage = function (page) {
            AdvertResource.getAdverts(page).then(function (items) {
                var pageList = [];
                for (i = 0; i < items.totalPages; i++) {
                    pageList[i] = i;
                }
                $scope.page = items;
                $scope.pageList = pageList;
                $scope.adverts = items.content;
            })
        }

        AdvertResource.getAdverts(0).then(function (items) {
            // angular.forEach(items, function(value, key) {
            //     PictureResource.getAdvertMainPicture(value.realestate.id).then(function (item) {
            //         value.main_pic = item.pictureName;
            //     })
            // });
            console.log(items);
            var pageList = [];
            for (i = 0; i < items.totalPages; i++) {
                pageList[i] = i;
            }
            console.log(items.totalPages);
            $scope.page = items;
            $scope.pageList = pageList;
            $scope.adverts = items.content;
        })
        

    })
})(angular);