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
        });

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
        	
        $scope.rating = 5;
        $scope.isReadonly = true;
        $scope.rateFunction = function(rating) {
        	console.log('Rating selected: ' + rating);
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
    app.directive("starRating", function(AdvertResource,$stateParams) {
    	  return {
    	    restrict : "EA",
    	    template : "<ul class='starRating' ng-class='{readonly: readonly}'>" +
    	               "  <li  class='starr' ng-repeat='star in stars' class='{filled: star.filled}'  ng-class='star' ng-click='toggle($index)'>" +
    	               "    <i class='fa fa-star'></i>" + //&#9733
    	               "  </li>" +
    	               "</ul>",
    	    scope : {
    	      ratingValue : "=ngModel",
    	      max : "=?", //optional: default is 5
    	      onRatingSelected : "&?",
    	      readonly: "=?",
    	      vr: "=?"
    	    },
    	    link : function(scope, elem, attrs) {
    	      if (scope.max == undefined) { scope.max = 5; }
    	      function updateStars() {
    	    	var aid = $stateParams.advertId;
    	    	
    	    	console.log(aid +" *****");
    	    	AdvertResource.getAdvert(aid).then(function (item){
    	    		var ad = item;
        	    	console.log(ad.advert_rate)
        	    	scope.vr = ad.advert_rate
        	        scope.stars = [];
        	        for (var i = 0; i < scope.max; i++) {
        	          scope.stars.push({
        	            filled : i < ad.advert_rate
        	          });
        	        }
    	    	});

    	      };
    	 
    	      scope.toggle = function(index) {
    	        if (scope.readonly == undefined || scope.readonly == false){
    	          scope.ratingValue = index + 1;
    	          scope.onRatingSelected({
    	            rating: index + 1
    	          });
    	        }
    	      };
    	      scope.$watch("ratingValue", function(oldVal, newVal) {
    	        if (newVal) { updateStars();}
    	      });
    	    }
    	  };
    	});

})(angular);