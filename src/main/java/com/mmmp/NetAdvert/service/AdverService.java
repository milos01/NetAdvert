package com.mmmp.NetAdvert.service;

import com.mmmp.NetAdvert.model.Advert;
import com.mmmp.NetAdvert.model.Comment;
import com.mmmp.NetAdvert.model.Customer;
import com.mmmp.NetAdvert.model.User;

public interface AdverService {
	
	public  void insert(Customer cus);
	
	public Advert findAdvert(int id);
	
	public void createComment(Comment comment);
	
	public User findUser(String email);
	
	public void deleteComment(Comment commentId);
	
	public Comment findComment(int id);
}
