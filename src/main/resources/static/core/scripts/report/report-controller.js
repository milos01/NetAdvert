(function(angular){
	app.controller('reportV',function(ReportResource,$scope,$state,_){
		
		ReportResource.getAllReports().then(function(response){
			$scope.reportList = response;
		});
		
		$scope.verifyAdvert = function(verify,report_id){
			console.log(verify+" "+report_id);
			console.log("zovem funkciju");
			ReportResource.verifyAdvertt(verify,report_id);
			console.log("zavrsio funkciju");
		}
	})
	

})(angular);