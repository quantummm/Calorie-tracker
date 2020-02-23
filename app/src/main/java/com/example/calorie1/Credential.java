package com.example.calorie1;

import java.util.Date;
/**
 * Create a credential class to store data of credential
 *
 * @author Rongzhi Wang
 * @version 1.1
 */
public class Credential {
    private String username;
    private String passwordhash;
    private Date signupdate;
    private Enduser userid;

    public Credential() {
    }

    public Credential(String username) {
        this.username = username;
    }

    /**
     * @return this user's username for login
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username a username for login
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return this user's hashed password
     */
    public String getPasswordhash() {
        return passwordhash;
    }

    /**
     * @param passwordhash user's password encrypted by hash
     */
    public void setPasswordhash(String passwordhash) {
        this.passwordhash = passwordhash;
    }

    /**
     * @return this user's register date
     */
    public Date getSignupdate() {
        return signupdate;
    }

    /**
     * @param signupdate a date that a new user regiter in
     */
    public void setSignupdate(Date signupdate) {
        this.signupdate = signupdate;
    }

    /**
     * @return a Enduser entity that records this user's personal information
     */
    public Enduser getUserid() { return userid; }

    /**
     * @param userid a Enduser entity that can store user personal information
     */
    public void setUserid(Enduser userid) { this.userid = userid; }

}
