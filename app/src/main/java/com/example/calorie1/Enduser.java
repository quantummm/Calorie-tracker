package com.example.calorie1;

import java.util.Date;

/**
 * Create a Enduser class to store a user personal information
 *
 * @author Rongzhi Wang
 * @version 1.1
 */
public class Enduser {
    private Integer userid;
    private String name;
    private String surname;
    private String email;
    private Date dob;
    private Integer height;
    private Integer weight;
    private String gender;
    private String address;
    private String postcode;
    private Integer levelofactivity;
    private Integer stepspermile;

    public Enduser() { }

    public Enduser(Integer userid) { this.userid = userid; }

    public Integer getUserid() { return userid; }

    public void setUserid(Integer userid) { this.userid = userid; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public Integer getLevelofactivity() {
        return levelofactivity;
    }

    public void setLevelofactivity(Integer levelofactivity) {
        this.levelofactivity = levelofactivity;
    }

    public Integer getStepspermile() {
        return stepspermile;
    }

    public void setStepspermile(Integer stepspermile) {
        this.stepspermile = stepspermile;
    }
}
