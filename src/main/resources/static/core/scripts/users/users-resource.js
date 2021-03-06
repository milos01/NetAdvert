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

        retVal.updateUser = function(email, first_name, last_name) {
            var p = Restangular.one('user');
            p.first_name = first_name;
            p.last_name = last_name;
            p.email = email;
            p.put().then(function(response) {});
        };
        
        retVal.getUserAdverts =  function (uid) {
            return Restangular.one("user", uid).
            getList("adverts").then(function(entries) {
                return entries;
            });
        }

        retVal.findKeywordsUser =  function (email) {
            return Restangular.one("getlikeUser").
            get({email: email}).then(function(entries) {
                return entries;
            });
        }

        retVal.findUserCompany = function (uid) {
            return Restangular.one("user", uid).one('mycompany').get().then(function (item) {
                return item;
            });
        }

        retVal.getAdvertsOfUser = function(idU){
        	return Restangular.one('advert').one('user',idU).getList().then(function(response){
        		return response;
        	})
        }
        retVal.getUserById = function(idU){
        	return Restangular.one('user',idU).get().then(function(res){
        		return res;
        	})
        }
        
        retVal.getUserRealestates = function () {
        	return Restangular.all("user/realestates").getList().then(function(entries) {
                return entries;
            });
        }
        
        return retVal;
    })
})(angular);