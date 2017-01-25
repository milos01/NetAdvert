/**
 * Created by milosandric on 08/01/2017.
 */
(function(angular){
    app.controller('advert', function ($rootScope, $location, $scope, _, $log, $stateParams, UsersResource, AdvertResource, PictureResource,$uibModal,CommentResource) {
        var aid = $stateParams.advertId;
        AdvertResource.getAdvert(aid).then(function (item) {
        	console.log("adverttt");	
        	console.log(item);
                PictureResource.getAdvertMainPicture(item.id).then(function (item2) {
                    $scope.mainPicture = item2.pictureName;
                    
                    $scope.advert = item;

                })
                CommentResource.getAllCommentsOfAd(aid).then(function(response){
        			$scope.commentList = response;
        		})
        		
        		// $scope.deleteComment = function(id){
        		// 	console.log("aaaaa");
        		// 	CommentResource.deleteComment(id);
        		// }
                
                
                AdvertResource.getUserAdverRaiting($scope.user.uid,item.id).then(function(response){
                	
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

			PictureResource.getAdvertPictures(aid).then(function (items) {
				$scope.pictures = items;
			});

			$scope.buyAdvert = function (advert) {
                AdvertResource.buyAdvert(advert.id).then(function (item) {
                	if(item){
                        advert.is_sold = true;
					}
                });
            }
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
		
	}]);

    app.controller('addAdvert1', function ($rootScope, $location, $scope, _, $log, $stateParams, UsersResource, AdvertResource, PictureResource, RealestateResource, $cookies, $http) {
    	if(!$rootScope.authenticated){
            $location.path("/");
        }
    	if($location.path()=='/newadvert/images' && $rootScope.uploadedAdvert===undefined){
    		$location.path("/");
    	};
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
        $scope.myFile = {};
        $scope.uploadFinished = true;
        $scope.selectedRealestate = {};
        $scope.selectedRealestate.category = {};
        $scope.selectedRealestate.type = {};
        $scope.existingRealestate = false;
        $scope.newAdvert = {};
        $scope.newAdvert.realestate = {};
        $scope.newAdvert.realestate.category = {};
        $scope.newAdvert.realestate.category.types = [];
        $scope.newAdvert.realestate.heating = false;
        $scope.newAdvert.rent_sale = false;
        //$scope.newAdvert.cost = 1;
        $scope.chooseType = [];
        $scope.newAdvert.realestate.equipments = [];
        
        $scope.updateType = function(){
        	if($scope.existingRealestate === false && $scope.newAdvert.realestate.category !== null){
        		$scope.chooseType = $scope.newAdvert.realestate.category.types;
        		RealestateResource.getCategoryEquipment($scope.newAdvert.realestate.category.id).then(function (items) {
        			$scope.categoryEquipment = items;
        			$scope.newAdvert.realestate.equipments = [];
        			for(var i = 0; i<items.length; i++){
        				$scope.newAdvert.realestate.equipments.push(false);
        			}
        		});
        	}
        	else{
        		$scope.chooseType = [];
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
        		$rootScope.uploadedAdvert = item;
        		console.log(item);
        		$location.path("/newadvert/images");
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
                //$scope.newAdvert.cost = 1;
                $scope.newAdvert.realestate.equipments = [];
                $scope.chooseType = [];
        	}
        };
        
        $scope.costNegative = function(){
        	if($scope.newAdvert.cost < 1){
        		$scope.newAdvert.cost = 1;
        	}
        }
        
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
        		//$scope.newAdvert.cost = 1;
        	}        
        };
        
        $scope.setimage = function() {
        	var file = $scope.myFile;
        	var reader = new FileReader();
        	reader.readAsDataURL(file);
        	reader.onload = function(e) {
        		$scope.$apply(function(){
        		      $scope.ImageSrc = e.target.result;
        		    });
        	}
        };
        
        $scope.deleteImage = function(){
        	if($scope.ImageSrc){
        		delete $scope.ImageSrc;
        		console.log($scope.myFile);
        		$scope.myFile = null;
        		console.log($scope.myFile);
        	}
        }
        
        $scope.fileChanged = function($event){
            var file = $event.target.files[0];
            var reader = new FileReader();
        	reader.readAsDataURL(file);
        	reader.onload = function(e) {
        		$scope.$apply(function(){
        		      $scope.ImageSrc = e.target.result;
        		    });
        	}
        }
        
        $scope.uploadFile = function() {
    		//alert("usoo");
            $scope.processDropzone();
        };

        $scope.reset = function() {
            $scope.resetDropzone();
        };
        
        $scope.sendImages = function(){
        	if($scope.myFile.name){
        		console.log("empty");
        	}
        	else{
        		console.log("nie");
        		console.log($scope.myFile);
        	}
        	if($scope.myFile!=null && $scope.myFile.name){
        	var fd = new FormData();
            fd.append('file', $scope.myFile);
            fd.append('realestate', $rootScope.uploadedAdvert.id);
			   fd.append('is_profile', true);
            $http.post('/api/upload', fd, {
               transformRequest: angular.identity,
               headers: {'Content-Type': undefined}
            })
            .then(function(){
            	$location.path('/advert/'+$rootScope.uploadedAdvert.id);
            },function(){
            });
        	}
        	else{
        		$location.path('/advert/'+$rootScope.uploadedAdvert.id);
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
                		console.log("stara nova");
                		console.log(oldValue);
                		console.log(newValue);
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
    
    app.directive('validInputs', function ($timeout) {
        return {
            restrict: 'A',
            scope: false,
            link: function (scope, element, attrs) {
                scope.$watch(function(scope){return scope.existingRealestate}, function (newValue, oldValue) {
                	$timeout(function(){
                		if(scope.existingRealestate){
                			scope.newAdvert.realestate.realestateName = "sadadasd ";
                			scope.newAdvert.realestate.area = "";
                			$("#form").validate().settings.ignore = ":disabled,:hidden";
                			$("#form").valid();
                		}
                		});
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
                		$("#selectRealestate").select2('val', '');
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
                scope.$watch(function(scope){return scope.existingRealestate}, function (newValue, oldValue) {
                	$timeout(function(){
                		if(scope.existingRealestate === false){
                			$("#selectCategory").select2('val', '');
                			$(".select2_demo_1").select2('val', '');
                			$("#form").validate().settings.ignore = "*";
                			$("#form").valid();
                		}
                		});
                });
            }
        };
    });
    
    app.directive('fileModel', function($parse, $timeout) {
        return {
            restrict: 'A',
            link: function($scope, element, attrs) {
            	var model = $parse(attrs.fileModel);
            	var modelSetter = model.assign;
            	$scope.$watch('myFile', function(e) {
            		console.log("uso dirke");
            		console.log($scope.myFile);
            		element.bind('change', function(e) {
            		      $scope.$apply(function(e) {
            		    	  console.log("aplyyy");
            		    	  console.log(element);
            		        modelSetter($scope, element[0].files[0]);
            		      });
            		      console.log("setuje se image");
            		      $scope.setimage();
            		    });
            		if($scope.myFile==null){
            			element.val("");
            		}
            		/*$timeout(function(){
            			$scope.$apply(function(e) {
            				modelSetter($scope, element[0].files[0]);
            			});
            			$scope.setimage();
            		});*/
            	});
            }
        };
    });
    
    app.directive("ngUploadChange",function(){
        return{
            scope:{
                ngUploadChange:"&"
            },
            link:function($scope, $element, $attrs){
                $element.on("change",function(event){
                    $scope.ngUploadChange({$event: event})
                })
                $scope.$on("$destroy",function(){
                    $element.off();
                });
            }
        }
    });
    
    app.directive('noDecimal', function() {
        return {
            require: 'ngModel',
            restrict: 'A',
            link: function(scope, element, attr, ctrl) {
                function inputValue(val) {
                    if (val) {
                        var value = val + ''; //convert to string
                        var digits = value.replace(/[^0-9]/g, '');
                        if (digits !== value) {
                            ctrl.$setViewValue(digits);
                            ctrl.$render();
                        }
                        return parseInt(digits);
                    }
                    return "";
                }
                ctrl.$parsers.push(inputValue);
            }
        };
    });
    
    app.directive('decimalPlaces',function(){
        return {
            link:function(scope,ele,attrs){
                ele.bind('keypress',function(e){
                    var newVal=$(this).val()+(e.charCode!==0?String.fromCharCode(e.charCode):'');
                    if($(this).val().search(/(.*)\.[0-9][0-9]/)===0 && newVal.length>$(this).val().length){
                        e.preventDefault();
                    }
                });
            }
        };
    });
    
    
    app.directive('yrInteger', function() {
        return {
            restrict: 'A',
            link: function(scope, element, attrs) {
              element.on('keypress', function(event) {
                if ( !isIntegerChar() ) 
                  event.preventDefault();
                function isIntegerChar() {
                  return /[0-9]|-/.test(
                    String.fromCharCode(event.which))
                }
              });       
            }
        }
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
    
    
    //dropzone directive
    function dropzone($http, $rootScope, $cookies) {

        return function($scope, element, attrs) {
        	var xsrfToken = $cookies.get('XSRF-TOKEN');
        	console.log(xsrfToken);
            var config = {
    			url: 'http://localhost:8080/api/upload',
    			uploadMultiple: false,
    			paramName: "file",
                maxFilesize: 100,
                paramName: "file",
                maxThumbnailFilesize: 10,
                parallelUploads: 1,
    			acceptedFiles: 'image/*',
    			addRemoveLinks: true,
    			headers: {
    		        'X-XSRF-TOKEN': xsrfToken
    		    },
    			init: function(){
    				this.on('sending', function(file, xhr, formData){
    					formData.append('realestate', $rootScope.uploadedAdvert.id);
    					formData.append('is_profile', false);
    					console.log("sending");
    					console.log($scope.uploadFinished);
    					
    					
    				        $scope.$apply(function () {
    				        	$scope.uploadFinished = false;
    				        });
    				    
    				});
    				this.on('success', function(file, response){
    					file.filename = response.pictureName
    				});
    				this.on("queuecomplete", function (file) {
    					console.log("queueComplete");
    					console.log($scope.uploadFinished);
    					
    					$scope.$apply(function () {
    						$scope.uploadFinished = true;
				        });
    			    });
    				this.on('removedfile', function(file){
    					//alert("usao");
    					//var fd = new FormData();
    					//fd.append('file', file);
    					//fd.append('realestate', 1);
    					//fd.append('image', 100);
    					$http.delete("/api/image/" + file.filename//, fd //, {
    						//transformRequest: angular.identity,
    						//headers: {'Content-Type': undefined}
    					//})
    					)	
    					.then(function(){
    					},function(){
    					});
    				});
    				//this.on("addedfile", handleFileAdded);
    				//this.on("removedfile", handleFileRemoved);
    				this.on("error", function(file){if (!file.accepted) this.removeFile(file);});
    				//this.on("addedfile", function(file) {
    					// Add default option box for each preview.
    					//var defaultRadioButton = Dropzone.createElement('<div class="default_pic_container"><input type="radio" name="default_pic" value="'+file.name+'" /> Make profile</div>');
    					//file.previewElement.appendChild(defaultRadioButton);
    				//});
    			}
            };

            var eventHandlers = {
                'addedfile': function(file) {
                   $scope.file = file;
    				//if (this.files.length == 1){
    				//	this.files[0]
    				//}
                    //if (this.files[1]!=null) {
                    //    this.removeFile(this.files[0]);
                    //}
                    $scope.$apply(function() {
                        $scope.fileAdded = true;
                    });
                },

                'success': function (file, response) {
                }
            };

            dropzone = new Dropzone(element[0], config);

            angular.forEach(eventHandlers, function(handler, event) {
                dropzone.on(event, handler);
            });

            $scope.processDropzone = function() {
                dropzone.processQueue();
            };

            $scope.resetDropzone = function() {
                dropzone.removeAllFiles();
            };
        }
        
    }

    app.directive('dropzone', dropzone);
    
})(angular);