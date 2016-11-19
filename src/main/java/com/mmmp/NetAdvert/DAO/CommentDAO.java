package com.mmmp.NetAdvert.DAO;

import com.mmmp.NetAdvert.model.Comment;

public interface CommentDAO {

	
	public void createComment(Comment comment);
	
	public void deleteComment(Comment commentId);
	
	public Comment findComment(int id);
}
