/**
 * Created by milosandric on 27/12/2016.
 */
(function(angular){
    app.factory('AdvertResource', function (Restangular, _) {
        var retVal = {};

        retVal.getAdverts = function(pageId, searchwords, heting, rent, sale, city, street, postal_code, region, min, max, amin, amax, ac, fridge, internet, cabletv, phone) {
            return Restangular.one("search").get({page:pageId, size:8, sort:"advertName,desc", heating: heting, advertName: searchwords, rent: rent, sale: sale, city: city, street: street, postalCode: postal_code, region: region, priceFrom: min, priceTo: max, areaFrom: amin, areaTo: amax, AC: ac, Fridge: fridge, Internet: internet,CableTV: cabletv, Phone:phone }).then(function(entries) {
                return entries;
            });
        };

        retVal.getAdvert = function (id) {
            return Restangular.one('advert', id).get().then(function (item) {
                return item;
            });
        };
        
        retVal.getUserAdverRaiting = function(idU,idA){
        	var userAdverR = {
        			user_id: idU,
        			advert_id: idA
        	}
        	console.log(userAdverR)
        	return Restangular.one('advert').one('findUserAdvertRait').get(userAdverR).then(function(response){
        		return response;
        	});
        };
        
        retVal.raitingAdvert = function(id,rating){
        	var addedRating = {rate: rating};
        	return Restangular.one('advert',id).all('rating').post(addedRating).then(function(response){
        		return response;
        	});
        };
        
        retVal.addAdvert = function(newAdvert) {
            return Restangular.all("advert").post(newAdvert).then(function(entries) {
                return entries;
            });
        };
        
        retVal.buyAdvert = function (aid) {
            return Restangular.one('advert', aid).one('buy').put().then(function (item) {
                return item;
            });
        }
        

        
        return retVal;
    })
})(angular);