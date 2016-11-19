package com.mmmp.NetAdvert.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mmmp.NetAdvert.DAO.AdvertDAO;
import com.mmmp.NetAdvert.DAO.CommentDAO;
import com.mmmp.NetAdvert.DAO.TestDAO;
import com.mmmp.NetAdvert.DAO.UserDAO;
import com.mmmp.NetAdvert.model.Advert;
import com.mmmp.NetAdvert.model.Comment;
import com.mmmp.NetAdvert.model.Customer;
import com.mmmp.NetAdvert.model.User;

@Service
public class AdvertServiceImplementation implements AdverService {

	
	@Autowired
	private TestDAO td;
	
	@Autowired
	private AdvertDAO adverDAO;
	
	@Autowired
	private CommentDAO commnetDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	
	@Override
	@Transactional
	public void insert(Customer customer) {
		this.td.insert(customer);
		
	}

	@Override
	@Transactional
	public Advert findAdvert(int id) {
		return this.adverDAO.findAdvert(id);
	}

	@Override
	@Transactional
	public void createComment(Comment comment) {
		this.commnetDAO.createComment(comment);
	}

	@Override
	@Transactional
	public User findUser(String email) {
		return this.userDAO.findUser(email);
	}

	@Override
	@Transactional
	public void deleteComment(Comment commentId) {
		this.commnetDAO.deleteComment(commentId);
	}

	@Override
	@Transactional
	public Comment findComment(int id) {
		return this.commnetDAO.findComment(id);
	}


}
