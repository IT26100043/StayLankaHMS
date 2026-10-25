package com.staylanka.staylanka.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true, unique = true)
    private String userName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false,unique = true)
    private String phoneNumber;
    private String Role;

    @Column(nullable = false)
    private String SecurityNumber;

    public User(){}

    public User(String userName,String password,String phoneNumber,String Role,String SecurityNumber){
        this.userName = userName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.Role = Role;
        this.SecurityNumber = SecurityNumber;
    }

    //setters


    public void setSecurityNumber(String securityNumber) {
        SecurityNumber = securityNumber;
    }

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


    public String getSecurityNumber() {
        return SecurityNumber;
    }

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
