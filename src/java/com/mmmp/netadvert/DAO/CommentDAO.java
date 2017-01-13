package com.mmmp.netadvert.DAO;

import java.util.List;

import com.mmmp.netadvert.model.Comment;
import com.mmmp.netadvert.model.Company;


public interface CommentDAO {

	
	public void createComment(Comment comment);
	
	public void deleteComment(Comment c);
	
	public Comment findComment(int id);
	
	public List<Comment> allCommentsOfAdvert(int advert_id);

}
