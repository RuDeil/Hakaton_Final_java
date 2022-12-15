package com.example.hakatonfinaljava.responses;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class LoginResponse extends BaseResponse implements Serializable {
    @SerializedName("token")
    private String token;
    @SerializedName("username")
    private String username;
    @SerializedName("accessToken")
    private String accessToken;
    @SerializedName("expiresIn")
    private long expiresIn;
    @SerializedName("user")
    private Integer user;
    @SerializedName("userID")
    private String userID;
    @SerializedName("macs")
    private ArrayList<MAC> macs;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String value) {
        this.username = value;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String value) {
        this.accessToken = value;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long value) {
        this.expiresIn = value;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer value) {
        this.user = value;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String value) {
        this.userID = value;
    }

    public ArrayList<MAC> getMacs() {
        return macs;
    }

    public void setMacs(ArrayList<MAC> macs) {
        this.macs = macs;
    }

}
