/**
 * Created by milosandric on 08/01/2017.
 */
(function(angular){
    app.controller('advert', function ($rootScope, $location, $scope, _, $log, $stateParams, UsersResource, AdvertResource, PictureResource,$uibModal,CommentResource) {
        var aid = $stateParams.advertId;
        AdvertResource.getAdvert(aid).then(function (item) {
                PictureResource.getAdvertMainPicture(item.realestate.id).then(function (item2) {
                    $scope.mainPicture = item2.pictureName;
                    $scope.advert = item;
                })
                CommentResource.getAllCommentsOfAd(aid).then(function(response){
        			$scope.commentList = response;
        		})
        		
        		$rootScope.deleteComment = function(id){
        			console.log("aaaaa");
        			CommentResource.deleteComment(id);
        		}
                
                
                AdvertResource.getUserAdverRaiting($scope.user.uid,item.id).then(function(response){
                	console.log(response.id + " " + response.advert_id + " " + response.user.id);
                	console.log(response);
                	 $scope.userAdverRate=response;
                })
                $scope.rating = item.advert_rate;
        });
        $scope.readonly  = true;
        $scope.rateAdvert = function(rate){
        	console.log(rate +" moja ocena");
	        	AdvertResource.raitingAdvert(aid,rate).then(function(response){
	        		console.log("trenutna ocena" + $scope.rating);
	        		var newRate = response.advert_rate;
	        		$scope.rating = newRate;
	        		$scope.advert = response;
	        		console.log("posle rejta ocena" + $scope.rating);
	                AdvertResource.getUserAdverRaiting($scope.user.uid,response.id).then(function(response){
	                	$scope.userAdverRate=response;
		        	})
	        	})
        }

        $scope.createComment = function(idA){
        	var text = $scope.textC;
        	CommentResource.createCommentt(idA,text).then(function(response){
        		$scope.commentList = response;
        	});
        }
        
        
		$scope.openReportModal = function(advert) {
        	var modalInstance = $uibModal.open({
        	   parent:'advert',
        	   templateUrl: 'core/views/modals/reportModal.html',
        	   controller: 'ReportModalCtrl',
        	   scope: $scope,   
        	   resolve: {
        	      advert: function() {
        	      return advert;
        	      }
        	   }
        	});
        
        	modalInstance.result.then(function(value) {
        	    $log.info('Modal finished its job at: ' + new Date() + ' with value: ' + value);
        		}, function(value) {
        	    $log.info('Modal dismissed at: ' + new Date() + ' with value: ' + value);
        	    });
        	};
        	
    		$scope.openMapModal = function(advertt) {
            	var modalInstance = $uibModal.open({
            	   parent:'advert',
            	   templateUrl: 'core/views/modals/mapModal.html',
            	   controller: 'mapModalCtrl',
            	   scope: $scope,   
            	   resolve: {
            	      advertt: function() {
            	      return advertt;
            	      }
            	   }
            	});
            
            	modalInstance.result.then(function(value) {
            	    $log.info('Modal finished its job at: ' + new Date() + ' with value: ' + value);
            		}, function(value) {
            	    $log.info('Modal dismissed at: ' + new Date() + ' with value: ' + value);
            	    });
            	};
    })
    
    app.controller('ReportModalCtrl',['$scope','$uibModalInstance','$log','_','ReportResource',function($scope,$uibModalInstance, $log, _,ReportResource) {
		
    	$scope.description = "";
    	$scope.ok = function() {
    		console.log($scope.advert.id + " " + $scope.description);
    		ReportResource.newReport($scope.description,$scope.advert.id);
			$uibModalInstance.close('ok');
		};

		$scope.cancel = function() {
			console.log("cancel");
			$uibModalInstance.dismiss('cancel');
		};
	}]);
    
    app.controller('mapModalCtrl',['$scope','$uibModalInstance','advertt',function($scope,$uibModalInstance,advertt) {
		console.log(advertt);
//		$scope.advertt1 = advertt;
//		var ad = advertt;
//		alert(ad.realestate.location.street);
//		var map;
//		var marker;
//
//		function initMap() {
//			map = new google.maps.Map(document
//					.getElementById('map'), {
//				center : {
//					lat : -34.397,
//					lng : 150.644
//				},
//				zoom : 16
//			});
//			var geocoder = new google.maps.Geocoder();
//			geocodeAddress(geocoder, map);
//
//		}
//		function geocodeAddress(geocoder, resultsMap) {
//			var address = ad.realestate.location.street + ","
//					+ ad.realestate.location.city + ",Srbija";
//			console.log(address);
//			geocoder
//					.geocode(
//							{
//								'address' : address
//							},
//							function(results, status) {
//								if (status === google.maps.GeocoderStatus.OK) {
//									resultsMap
//											.setCenter(results[0].geometry.location);
//									var marker = new google.maps.Marker(
//											{
//												map : resultsMap,
//												position : results[0].geometry.location,
//												title : ad.realestate.location.street
//
//											});
//								} else {
//									alert('Geocode was not successful for the following reason: '
//											+ status);
//								}
//							});
//		}
//
//		$scope.cancel = function() {
//			console.log("cancel");
//			$uibModalInstance.dismiss('cancel');
//		};
	}]);

})(angular);