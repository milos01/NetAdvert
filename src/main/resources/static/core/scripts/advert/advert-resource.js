/**
 * Created by milosandric on 27/12/2016.
 */
(function(angular){
    app.factory('AdvertResource', function (Restangular, _) {
        var retVal = {};

        retVal.getAdverts = function(pageId) {
            return Restangular.one("advert").get({page:pageId, size:1}).then(function(entries) {
                return entries;
            });
        };
        return retVal;
    })
})(angular);