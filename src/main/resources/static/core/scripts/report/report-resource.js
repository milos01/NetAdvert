(function(angular){
	
	app.factory('ReportResource', function(Restangular,_){
		//Logika za rad sa backendom
		var report = {};
		var reportList = [];
		report.getAllReports = function(){
			return Restangular.all("report").getList().then(function(response) {
				reportList = response;
				return reportList;
            });
		}
		

		report.verifyAdvertt = function(verify, report_id) {

			return Restangular.one('report').put({verify:verify,report_id:report_id}).then(function(response){
				console.log(verify+" "+report_id);
				_.remove(reportList, {
					id : response.id
				});
				console.log("bbbbbbbbbbb");
			});
//			console.log(verify+" "+report_id);
//			var r = Restangular.one('report/update');
//			r.verify = verify;
//			r.report_id = report_id;
//			r.put().then(function(response) {
//				console.log("aaaaaaaa");
//				_.remove(reportList, {
//					id : response.data.id
//				});
//				console.log("bbbbbbbbbbb");
//			});
		}
		
		return report;
	})
	
})(angular);