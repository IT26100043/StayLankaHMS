package com.staylanka.staylanka.entity;

public class RequestUserDataByUserName {
    private String UserName;
    private String Password;

    public RequestUserDataByUserName(){}

    //getters

    public String getUserName() {
        return UserName;
    }

    public String getPassword() {
        return Password;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
