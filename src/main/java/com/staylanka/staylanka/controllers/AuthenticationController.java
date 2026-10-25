package com.staylanka.staylanka.controllers;

import com.staylanka.staylanka.entity.AdminUser;
import com.staylanka.staylanka.entity.CustomerUser;
import com.staylanka.staylanka.entity.RequestForgotPassword;
import com.staylanka.staylanka.entity.RequestUserDataByUserName;
import com.staylanka.staylanka.service.AuthenticationService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")

public class AuthenticationController {

    public final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService){
        this.authenticationService = authenticationService;
    }

    @PostMapping("/admin")
    public AdminUser checkAdminUserAuth(@RequestBody RequestUserDataByUserName userLoginData){
        return authenticationService.checkAdminAuthentication(userLoginData);
    }

    @PostMapping("/customer")
    public CustomerUser checkCustomerAuth(@RequestBody RequestUserDataByUserName requestUserDataByUserName){
        return authenticationService.checkCustomerAuthentication(requestUserDataByUserName);
    }

    @PutMapping("/forgot")
    public CustomerUser forgotPassword(@RequestBody RequestForgotPassword requestForgotPassword){
        return authenticationService.forgotPassword(requestForgotPassword);
    }

}
