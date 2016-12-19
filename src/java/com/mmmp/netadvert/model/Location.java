package com.mmmp.netadvert.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Set;


@Entity
public class Location implements Serializable {
    
	private static final long serialVersionUID = -7354773052507981930L;

	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    private String street;

    @Column(name="street_number")
    private int streetNumber;

    private String region;

    private String city;

    @Column(name="postal_code")
    private int postalCode;

    @JsonBackReference
    @OneToMany(mappedBy="location", fetch=FetchType.LAZY)
    private Set<Realestate> realestates;

    public Location(){}
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(int streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public Set<Realestate> getRealestates() {
        return realestates;
    }

    public void setRealestates(Set<Realestate> realestates) {
        this.realestates = realestates;
    }
}
