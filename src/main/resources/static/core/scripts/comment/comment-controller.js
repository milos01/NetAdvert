(function(angular){
	
	app.controller('commentController',function(CommentResource,$scope){
		$scope.deleteComment = function(id){
			console.log("aaaaa");
			CommentResource.deleteComment(id);
		}
	})

})(angular);