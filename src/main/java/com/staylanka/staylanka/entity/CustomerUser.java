package com.staylanka.staylanka.entity;


import jakarta.persistence.Entity;


@Entity
public class CustomerUser extends User{
    public CustomerUser(){}

    public CustomerUser(String userName,String password,String phoneNumber,String securityNumber){
        super(userName,password,phoneNumber,"CUSTOMER",securityNumber);
    }
}
