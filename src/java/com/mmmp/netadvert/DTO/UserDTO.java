package com.mmmp.netadvert.DTO;

import com.mmmp.netadvert.model.User;

/**
 * Created by milosandric on 14/12/2016.
 */
public class UserDTO {
    private int id;
    String email;
    private String first_name;
    private String last_name;
    private String password;
    private int user_rate;
    private RoleDTO role;

    public UserDTO() {

    }

    public UserDTO(User user) {
        id = user.getId();
        email = user.getEmail();
        first_name = user.getFirst_name();
        last_name = user.getLast_name();
        password = user.getPassword();
        user_rate = user.getUser_rate();
        role = new RoleDTO(user.getRole());

    }

    public RoleDTO getRole() {
        return role;
    }

    public void setRole(RoleDTO role) {
        this.role = role;
    }

    public int getUser_rate() {
        return user_rate;
    }

    public void setUser_rate(int user_rate) {
        this.user_rate = user_rate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }
}
