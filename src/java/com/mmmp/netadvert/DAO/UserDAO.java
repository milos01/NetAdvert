package com.mmmp.netadvert.DAO;

import com.mmmp.netadvert.model.User;
import com.mmmp.netadvert.model.Role;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.List;

public interface UserDAO {

	public User findUser(String email);

	public Boolean RegisterUser(User user);
	
	public Boolean updateUser(User luser);

	public List<User> getAllUsers();

	public User findUserByCreds(String username, String password);

	public User findUserById(int id);
}
