(function(angular){
	
	app.controller('comment',function(CommentResource,$scope,_,$rootScope){
		
		CommentResource.getAllCommentsOfAd(id).then(function(response){
			$scope.commentList = response;
		});
		
//		$rootScope.deleteComment = function(id){
//			console.log("aaaaa");
//			CommentResource.deleteComment(id);
//		}
	})
})(angular);