package com.staylanka.staylanka.controllers;

import com.staylanka.staylanka.entity.AdminUser;
import com.staylanka.staylanka.entity.CustomerUser;
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
    public CustomerUser checkCustomerAuth(@RequestBody CustomerUser customerUser){
        return authenticationService.checkCustomerAuthentication(customerUser);
    }

}
