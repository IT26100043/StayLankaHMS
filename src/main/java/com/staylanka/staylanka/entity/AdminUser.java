package com.staylanka.staylanka.entity;

public class AdminUser extends User {

    public AdminUser(){}

    public AdminUser(String userName,String password,String phoneNumber){
        super(userName,password,phoneNumber,"ADMIN");
    }
}
