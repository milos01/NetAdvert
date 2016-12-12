package com.mmmp.netadvert.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;

/**
 * Created by milosandric on 12/12/2016.
 */

@Entity
public class Picture {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Column(name="picture_name")
    private String pictureName;

    @JsonManagedReference
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @JsonManagedReference
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="realestate_id", nullable=false)
    private Realestate realestate;

    @Column(name="is_profile")
    private boolean isProfile;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Realestate getRealestate() {
        return realestate;
    }

    public void setRealestate(Realestate realestate) {
        this.realestate = realestate;
    }

    public boolean isProfile() {
        return isProfile;
    }

    public void setProfile(boolean isProfile) {
        this.isProfile = isProfile;
    }


}
