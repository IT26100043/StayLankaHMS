package com.staylanka.staylanka.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class CustomerUser extends User{

    public CustomerUser(){}

    public CustomerUser(String userName,String password,String phoneNumber){
        super(userName,password,phoneNumber,"CUSTOMER");
    }
}
