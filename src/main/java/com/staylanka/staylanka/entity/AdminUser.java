package com.staylanka.staylanka.entity;


import jakarta.persistence.Entity;

@Entity
public class AdminUser extends User {

    public AdminUser(){}

    public AdminUser(String userName,String password,String phoneNumber){
        super(userName,password,phoneNumber,"ADMIN");
    }
}
