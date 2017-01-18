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

        retVal.getAdvert = function (id) {
            return Restangular.one('advert', id).get().then(function (item) {
                return item;
            });

        }
        
        retVal.getUserAdverRaiting = function(idU,idA){
        	var userAdverR = {
        			user_id: idU,
        			advert_id: idA
        	}
        	console.log(userAdverR)
        	return Restangular.one('advert').one('findUserAdvertRait').get(userAdverR).then(function(response){
        		return response;
        	});
        }
        
        retVal.raitingAdvert = function(id,rating){
        	var addedRating = {rate: rating};
        	return Restangular.one('advert',id).all('rating').post(addedRating).then(function(response){
        		return response;
        	});
        }
        
        return retVal;
    })
})(angular);