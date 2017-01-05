package com.mmmp.netadvert.DAO;

import com.mmmp.netadvert.model.User;

import java.util.List;

public interface UserDAO {

	public User findUser(String email);

	public Boolean RegisterUser(User user);
	
	public Boolean updateUser(User luser);

	public List<User> getAllUsers();

	public User findUserByCreds(String username, String password);

	public User findUserById(int id);

    public User getlikeUser(String email);
}
