package com.mmmp.netadvert.DAO;

import com.mmmp.netadvert.model.User;
import com.mmmp.netadvert.model.Role;

import java.util.List;

public interface UserDAO {

	public User findUser(String email);

	public Boolean RegisterUser(User user);
	
	public User updateUser(User luser, User user, Role role);

	public List<User> getAllUsers();

	public User findUserByCreds(String username, String password);

	public User findUserById(int id);
}
