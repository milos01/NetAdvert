/**
 * Created by milosandric on 23/12/2016.
 */
(function(angular){
    app.controller('home', function ($rootScope, $location, $scope, _, $log, UsersResource, AdvertResource, PictureResource) {
        if(!$rootScope.authenticated){
            $location.path("/");
        }

        $scope.loadNewPage = function (page) {
            AdvertResource.getAdverts(page).then(function (items) {
                angular.forEach(items.content, function(value, key) {
                    PictureResource.getAdvertMainPicture(value.realestate.id).then(function (item) {
                        value.main_pic = item.pictureName;
                        $scope.adverts = items.content;
                    })
                });
                var pageList = [];
                for (i = 0; i < items.totalPages; i++) {
                    pageList[i] = i;
                }
                $scope.page = items;
                $scope.pageList = pageList;

            })
        }

        $scope.openAdvertPage = function (advert) {
            $scope.advert = advert;
        }

        AdvertResource.getAdverts(0).then(function (items) {
            angular.forEach(items.content, function(value, key) {
                PictureResource.getAdvertMainPicture(value.realestate.id).then(function (item) {
                    value.main_pic = item.pictureName;
                })
            });
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