(function(angular){
	app.controller('userProfile',function($rootScope,$stateParams, $location, $scope, _, $log, UsersResource,UserRatingResource){
		
		 var idU = $stateParams.userId;
		 console.log(idU);
		 UsersResource.getAdvertsOfUser(idU).then(function(response){
			 $scope.advertList = response;
		 })
		 
		 UsersResource.getUserById(idU).then(function(u){
			 $scope.firstName = u.first_name;
			 $scope.lastName = u.last_name;
			 $scope.email = u.email;
			 $scope.userRate = u.user_rate;
			 $scope.userProf = u.id;
		 })
		 
		 UserRatingResource.getUserUserRaiting($scope.user.uid,idU).then(function(res){
			 $scope.userAdverRate=res;
		 })
		 $scope.readonly  = true;
		 
		 $scope.rateUser = function(rate){
	        	console.log(rate +" moja ocena");
	        	UserRatingResource.ratingUser(idU,rate).then(function(response){
		        		console.log("trenutna ocena" + $scope.userRate);
		        		var newRate = response.user_rate;
		        		$scope.userRate = newRate;
		        		console.log("posle rejta ocena" + $scope.userRate);
		        		UserRatingResource.getUserUserRaiting($scope.user.uid,response.id).then(function(response2){
		                	$scope.userAdverRate=response2;
			        	})
		        	})
	        }
	})
})(angular);