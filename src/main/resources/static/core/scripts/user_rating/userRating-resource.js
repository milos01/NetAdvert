(function(angular){
	app.factory('UserRatingResource',function(Restangular,_){
		
		var userRate = {};
		
		userRate.getUserUserRaiting = function(idU,idUP){
			var userRate = {
					user_id: idU,
					user_idP: idUP
			}
			return Restangular.one('user').one('findUserUserRait').get(userRate).then(function(response){
				return response;
			})
		}
		
		userRate.ratingUser = function(id,rating){
			var addedRating = {rate: rating};
        	return Restangular.one('user',id).all('rating').post(addedRating).then(function(response){
        		return response;
        	});
		}
		
		return userRate;
	})
})(angular);