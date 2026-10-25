package com.staylanka.staylanka.entity;


import jakarta.persistence.Entity;


@Entity
public class CustomerUser extends User{
    public CustomerUser(){}

    public CustomerUser(String userName,String password,String phoneNumber,String SecurityNumber){
        super(userName,password,phoneNumber,"CUSTOMER",SecurityNumber);
    }
}
