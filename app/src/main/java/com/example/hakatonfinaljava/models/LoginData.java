package com.example.hakatonfinaljava.models;

public class LoginData {
    private String phone;
    private String password;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getGuest() {
        return guest;
    }

    public void setGuest(Integer guest) {
        this.guest = guest;
    }

    private Integer  guest;


    public LoginData(String phone, String password, Integer guest) {
        this.phone = phone;
        this.password = password;
        this.guest = guest;
    }

}
