package com.mmmp.netadvert.DAO;

import java.util.List;

import com.mmmp.netadvert.model.Comment;


public interface CommentDAO {

	
	public void createComment(Comment comment);
	
	public void deleteComment(Comment commentId);
	
	public Comment findComment(int id);
	
	public List<Comment> allCommentsOfAdvert(int advert_id);
}
