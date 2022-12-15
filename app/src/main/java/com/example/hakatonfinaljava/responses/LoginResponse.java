package com.example.hakatonfinaljava.responses;

import java.util.ArrayList;

public class LoginResponse extends BaseResponse {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    private String username;
    private String accessToken;
    private long expiresIn;
    private Integer user;
    private String userID;
    private ArrayList<MAC>  macs;

    public ArrayList<MAC> getMacs() {
        return macs;
    }

    public void setMacs(ArrayList<MAC> macs) {
        this.macs = macs;
    }



    public String getUsername() { return username; }
    public void setUsername(String value) { this.username = value; }

    public String getAccessToken() { return accessToken; }
    public void setAccessToken(String value) { this.accessToken = value; }

    public long getExpiresIn() { return expiresIn; }
    public void setExpiresIn(long value) { this.expiresIn = value; }

    public Integer getUser() { return user; }
    public void setUser(Integer value) { this.user = value; }

    public String getUserID() { return userID; }
    public void setUserID(String value) { this.userID = value; }








}
