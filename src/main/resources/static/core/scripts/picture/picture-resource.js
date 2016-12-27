/**
 * Created by milosandric on 27/12/2016.
 */
(function(angular){
    app.factory('PictureResource', function (Restangular, _) {
        var retVal = {};

        retVal.getAdvertMainPicture = function(id) {
            return Restangular.one("advert", id).one("mainPicture").get();
        };
        return retVal;
    })
})(angular);