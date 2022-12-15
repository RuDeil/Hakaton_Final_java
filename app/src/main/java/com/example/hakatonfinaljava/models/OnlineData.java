package com.example.hakatonfinaljava.models;


public class OnlineData {
    private String mac;
    private String user_id;

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public OnlineData(String mac, String user_id) {
        this.mac = mac;
        this.user_id = user_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
