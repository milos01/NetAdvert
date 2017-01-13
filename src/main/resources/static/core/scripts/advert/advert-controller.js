/**
 * Created by milosandric on 08/01/2017.
 */
(function(angular){
    app.controller('advert', function ($rootScope, $location, $scope, _, $log, $stateParams, UsersResource, AdvertResource, PictureResource,$uibModal) {
        var aid = $stateParams.advertId;
        AdvertResource.getAdvert(aid).then(function (item) {
                PictureResource.getAdvertMainPicture(item.realestate.id).then(function (item2) {
                    $scope.mainPicture = item2.pictureName;
                    $scope.advert = item;
                })
        });

        
        
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
    
})(angular);