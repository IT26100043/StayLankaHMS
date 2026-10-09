package com.staylanka.staylanka.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;
    private String password;
    private String phoneNumber;
    private String Role;

    public User(){}

    public User(String userName,String password,String phoneNumber,String Role){
        this.userName = userName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.Role = Role;

    }

    //setters


    public void setId(Long id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setRole(String role) {
        Role = role;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    //getters


    public Long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return Role;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getUserName() {
        return userName;
    }
}
