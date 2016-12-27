/**
 * Created by milosandric on 23/12/2016.
 */
(function(angular){
    app.factory('UsersResource', function (Restangular, _) {
        var retVal = {};

        retVal.getStudents = function() {
            return Restangular.all("allusers").getList().then(function(entries) {
                return entries;
            });
        };
        return retVal;
    })
})(angular);