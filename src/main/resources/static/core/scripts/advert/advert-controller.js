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

    app.controller('addAdvert', function ($rootScope, $location, $scope, _, $log, $stateParams, UsersResource, AdvertResource, PictureResource, RealestateResource) {
        RealestateResource.getCategories().then(function (items) {
            $scope.categories = items;
        });
        
        RealestateResource.getTypes().then(function (items) {
            $scope.types = items;
        });
        UsersResource.getUserRealestates().then(function (items) {
            $scope.userRealestates = items;
            console.log($scope.userRealestates);
        });
        $scope.selectedRealestate = {};
        $scope.selectedRealestate.category = {};
        $scope.selectedRealestate.type = {};
        $scope.existingRealestate = false;
        $scope.newAdvert = {};
        $scope.newAdvert.realestate = {};
        $scope.newAdvert.realestate.heating = false;
        $scope.newAdvert.rent_sale = false;
        $scope.chooseType = [];
        $scope.newAdvert.realestate.equipments = [];
        $scope.updateType = function(){
        	if($scope.existingRealestate === false){
        		console.log($scope.newAdvert);
        		$scope.chooseType = $scope.newAdvert.realestate.category.types;
        		RealestateResource.getCategoryEquipment($scope.newAdvert.realestate.category.id).then(function (items) {
        			$scope.categoryEquipment = items;
        			$scope.newAdvert.realestate.equipments = [];
        			for(var i = 0; i<items.length; i++){
        				$scope.newAdvert.realestate.equipments.push(false);
        			}
        		});
        	}
        	
        };
        $scope.submitAdvert = function(){
        	console.log($scope.newAdvert);
        	var cat = {};
        	cat.realestateCategoryId = $scope.newAdvert.realestate.category.id;
        	cat.categoryName = $scope.newAdvert.realestate.category.categoryName;
        	$scope.newAdvert.realestate.category = cat;
        	$scope.newAdvert.realestate.type.realestateTypeId = $scope.newAdvert.realestate.type.id;
        	delete $scope.newAdvert.realestate.type.id;
        	AdvertResource.addAdvert($scope.newAdvert).then(function (item){
        		console.log(item);
        	});
        };
        
        $scope.disableInputs = function(){
        	if($scope.existingRealestate === true){
        		console.log($scope.selectedReaelstate);
        		$scope.selectedRealestate = {};
        		$scope.selectedRealestate.category = {};
        		$scope.selectedRealestate.type = {};
        	}
        	else{
        		$scope.newAdvert = {};
                $scope.newAdvert.realestate = {};
                $scope.newAdvert.realestate.heating = false;
                $scope.newAdvert.rent_sale = false;
                $scope.newAdvert.realestate.equipments = [];
        	}
        };
        
        $scope.updateForm = function(){
        	if($scope.selectedRealestate != null){
        		console.log("selectedReal");
        		console.log($scope.selectedRealestate);
        		$scope.newAdvert = {};
        		$scope.newAdvert.realestate = {};
        		$scope.newAdvert.realestate.category = {};
        		$scope.newAdvert.realestate.type = {};
        		$scope.newAdvert.realestate.location = {};
        		$scope.newAdvert.realestate.equipments = [];
        		$scope.newAdvert.realestate.realestateId = $scope.selectedRealestate.id;
        		$scope.newAdvert.realestate.realestateName = $scope.selectedRealestate.realestateName;
        		$scope.newAdvert.realestate.area = $scope.selectedRealestate.area;
        		$scope.newAdvert.realestate.heating = $scope.selectedRealestate.heating;
        		$scope.newAdvert.realestate.category = $scope.selectedRealestate.category;
        		$scope.newAdvert.realestate.type = $scope.selectedRealestate.type;
        		RealestateResource.getCategoryEquipment($scope.selectedRealestate.category.id).then(function (items) {
        			$scope.categoryEquipment = items;
        			$scope.newAdvert.realestate.equipments = [];
        			var exists;
        			for(var i = 0; i<items.length; i++){
        				exists = false;
        				for(var j = 0; j<$scope.selectedRealestate.technicalEquipments.length; j++){
        					if(items[i].id === $scope.selectedRealestate.technicalEquipments[j].id){
        						$scope.newAdvert.realestate.equipments.push(true);
        						exists = true;
        						break;
        					}
        				}
        				if(!exists){
        					$scope.newAdvert.realestate.equipments.push(false);
        				}
        			}
        		});
        		$scope.newAdvert.realestate.location = $scope.selectedRealestate.location;
        		$scope.newAdvert.rent_sale = false;
        	}        
        };
        
    })
    
    app.directive('updateSelect2', function ($timeout) {
        return {
            restrict: 'A',
            scope: false,
            link: function (scope, element, attrs) {
                scope.$watch(function(scope){return scope.chooseType}, function (newValue, oldValue) {
                	$timeout(function(){
                		  // Any code in here will automatically have an $scope.apply() run afterwards
                		$(".select2_demo_1").select2('val', '');
                		console.log(scope.newAdvert);
                		console.log(scope.categoryEquipment);
                		console.log(scope.newAdvert.realestate.equipments);
                		  // And it just works!
                		});
                	//$(".select2_demo_1").select2('val', '');
                });
            }
        };
    });
    
    app.directive('updateRealestate', function ($timeout) {
        return {
            restrict: 'A',
            scope: false,
            link: function (scope, element, attrs) {
                scope.$watch(function(scope){return scope.existingRealestate}, function (newValue, oldValue) {
                	$timeout(function(){
                		$(".selectRealestate").select2('val', '');
                		});
                });
            }
        };
    });
    
    app.directive('updateCategory', function ($timeout) {
        return {
            restrict: 'A',
            scope: false,
            link: function (scope, element, attrs) {
                scope.$watch(function(scope){return scope.selectedRealestate}, function (newValue, oldValue) {
                	$timeout(function(){
                		console.log("selectedRealDirektiva");
                		console.log(scope.selectedRealestate);
                		if(scope.selectedRealestate != null){
                			for(var c in scope.categories){
                				console.log(scope.categories[c]);
                				console.log(c);
                				if(scope.categories[c].categoryName.valueOf() == scope.selectedRealestate.category.categoryName.valueOf()){
                					console.log("poklopilo se");
                					console.log(scope.categories[c]);
                					//$(".selectCategory").select2('val', JSON.stringify(scope.categories[c]);
                					//$(".selectCategory").val(JSON.stringify(scope.categories[c])).trigger('change.select2');
                					//$(".selectCategory").select2('data', scope.categories[c]);
                					//$(".selectCategory").val(scope.categories[c]);
                					//$(".selectCategory").val(scope.categories[c]).trigger('change.select2');
                					//$(".selectCategory").select2("trigger", "select", scope.categories[c]);
                					//$(".selectCategory").val(scope.categories[c]).trigger("change");
                					break;
                				}
                			}
                			//$(".selectCategory").select2('val', 'dasdasdas');
                			//$(".selectCategory").val(scope.selectedRealestate.category.categoryName).trigger("change");
                		}
                		});
                });
            }
        };
    });
    
    
    /**
     * icheck - Directive for custom checkbox icheck
     */
    function icheck($timeout) {
        return {
            restrict: 'A',
            require: 'ngModel',
            link: function($scope, element, $attrs, ngModel) {
                return $timeout(function() {
                    var value;
                    value = $attrs['value'];

                    $scope.$watch($attrs['ngModel'], function(newValue){
                        $(element).iCheck('update');
                    })

                    return $(element).iCheck({
                        checkboxClass: 'icheckbox_square-green',
                        radioClass: 'iradio_square-green'

                    }).on('ifChanged', function(event) {
                            if ($(element).attr('type') === 'checkbox' && $attrs['ngModel']) {
                                $scope.$apply(function() {
                                    return ngModel.$setViewValue(event.target.checked);
                                });
                            }
                            if ($(element).attr('type') === 'radio' && $attrs['ngModel']) {
                                return $scope.$apply(function() {
                                    return ngModel.$setViewValue(value);
                                });
                            }
                        });
                });
            }
        };
    }
    
    app.directive('icheck', icheck);
    
})(angular);