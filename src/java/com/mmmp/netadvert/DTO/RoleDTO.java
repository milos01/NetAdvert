package com.mmmp.netadvert.DTO;

import com.mmmp.netadvert.model.Role;

/**
 * Created by milosandric on 14/12/2016.
 */
public class RoleDTO {
    private int id;
    private String name;

    public RoleDTO(Role role){
        this(role.getId(), role.getName());
    }

    public RoleDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
