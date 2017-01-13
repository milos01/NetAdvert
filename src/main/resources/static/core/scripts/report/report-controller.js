(function(angular){
	app.controller('reportV',function(ReportResource,$scope,_,$uibModal,$rootScope){
		
		ReportResource.getAllReports().then(function(response){
			$scope.reportList = response;
		});
		
		$scope.verifyAdvert = function(verify,report_id){
			ReportResource.verifyAdvertt(verify,report_id);
		}
	})

})(angular);