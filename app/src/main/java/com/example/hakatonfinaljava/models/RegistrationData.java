package com.example.hakatonfinaljava.models;

public class RegistrationData {
    private String phone;
    private String uname;
    private String password;

    public RegistrationData(String phoneNumber, String fio, String pass) {
        this.phone = phoneNumber;
        this.uname = fio;
        this.password = pass;
    }
}
