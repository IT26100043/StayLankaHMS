package com.staylanka.staylanka.service;

import com.staylanka.staylanka.entity.AdminUser;
import com.staylanka.staylanka.repository.AdminUserRepository;
import com.staylanka.staylanka.repository.CustomerRepository;

public class AuthenticationService {

    public final AdminUserRepository adminUserRepository;
    public final CustomerRepository customerRepository;
    public AuthenticationService(AdminUserRepository adminUserRepository,CustomerRepository customerRepository){
        this.adminUserRepository = adminUserRepository;
        this.customerRepository = customerRepository;
    }

    public AdminUser checkAdminAuthentication(AdminUser adminUser){
        return adminUser;
    }

    
}
