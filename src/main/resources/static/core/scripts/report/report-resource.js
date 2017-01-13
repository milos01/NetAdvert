(function(angular){
	
	app.factory('ReportResource', function(Restangular,_){
		var report = {};
		var reportList = [];
		report.getAllReports = function(){
			return Restangular.all("report").getList().then(function(response) {
				reportList = response;
				return reportList;
            });
		}
		

		report.verifyAdvertt = function(verify, report_id) {
		
			var verReport = {
					verify: verify,
					report_id: report_id
			}
			console.log(report_id);
			return Restangular.all('report').customPUT(verReport).then(function(response){
				
				_.remove(reportList, {
					id : response.id
				});
				for(i=0; i < reportList.length; i++){
					console.log(reportList[i].advert.id + " id ad res " + response.advert.id);
					if (reportList[i].advert.id == response.advert.id){
						console.log("brisem preostale");
						_.remove(reportList, {
							id : reportList[i].id
						});
					}
				}
				
			});
		}
		
		report.newReport = function(desc,idA){
			var repo = {
					reportDescription:desc,
					advert_id:idA
			}
			console.log(repo.advert_id)
			return Restangular.all('report').post(repo).then(function(response){
				reportList.push(response);
				return response;
			});
		}
		
		return report;
	})
	
})(angular);