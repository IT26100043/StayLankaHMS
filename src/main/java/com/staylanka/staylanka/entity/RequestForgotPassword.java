package com.staylanka.staylanka.entity;

public class RequestForgotPassword {
    private String userName;
    private String password;
    private String SecurityNumber;
    private String phoneNumber;

    public RequestForgotPassword(){}

    //getters

    public String getSecurityNumber() {
        return SecurityNumber;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    //setters

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSecurityNumber(String securityNumber) {
        SecurityNumber = securityNumber;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
