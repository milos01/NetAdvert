/**
 * Created by milosandric on 27/12/2016.
 */
(function(angular){
    app.factory('PictureResource', function (Restangular, _) {
        var retVal = {};

        retVal.getAdvertMainPicture = function(id) {
            return Restangular.one("advert", id).one("mainPicture").get();
        };

        retVal.getAdvertPictures = function (aid) {
            return Restangular.one("advert", aid).all("pictures").getList().then(function (items) {
                return items;
            });
        }

        return retVal;
    })
})(angular);