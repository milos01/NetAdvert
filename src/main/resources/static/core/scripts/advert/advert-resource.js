/**
 * Created by milosandric on 27/12/2016.
 */
(function(angular){
    app.factory('AdvertResource', function (Restangular, _) {
        var retVal = {};

        retVal.getAdverts = function() {
            return Restangular.all("advert").getList().then(function(entries) {
                return entries;
            });
        };
        return retVal;
    })
})(angular);