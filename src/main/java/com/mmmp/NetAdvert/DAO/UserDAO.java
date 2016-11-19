package com.mmmp.NetAdvert.DAO;

import com.mmmp.NetAdvert.model.User;

public interface UserDAO {

	public User findUser(String email);

	public Boolean RegisterUser(User user);
}
