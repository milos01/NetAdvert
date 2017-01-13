(function(angular){
	
	app.factory('CommentResource',function(Restangular,_){
		
		var comment = {};
		var commentList = [];
		
		comment.getAllCommentsOfAd = function(id){
			
			return Restangular.all('comment').one('advert',id).getList().then(function(response){
				commentList = response;
				return commentList;
			});
			
		}
		
		comment.createCommentt = function(id,text){
			var commentt = {
					advert_id:id,
					text:text
			}
			
			return Restangular.all('comment').post(commentt).then(function(response){
				commentList.push(response);
				return commentList;
			});
		}
		
		comment.deleteComment = function(id){
			return Restangular.all('comment').one('delete',id).remove().then(function(){
				_.remove(commentList, {
          			id: id
        		});
			})
		}
		
		return comment;
	})
	
})(angular);