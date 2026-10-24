package com.staylanka.staylanka.service;

import com.staylanka.staylanka.entity.AdminUser;
import com.staylanka.staylanka.entity.CustomerUser;
import com.staylanka.staylanka.repository.AdminUserRepository;
import com.staylanka.staylanka.repository.CustomerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthenticationService {

    public final AdminUserRepository adminUserRepository;
    public final CustomerRepository customerRepository;
    public AuthenticationService(AdminUserRepository adminUserRepository,CustomerRepository customerRepository){
        this.adminUserRepository = adminUserRepository;
        this.customerRepository = customerRepository;
    }

    public AdminUser checkAdminAuthentication(AdminUser adminUser){
        List<AdminUser> existAdminUser = adminUserRepository.findAdminUserByUserName(adminUser.getUserName());

        if(existAdminUser.isEmpty()){
            return null;
        }
        AdminUser selectAdminUser = existAdminUser.get(0);

        if(selectAdminUser.getPassword().equals(adminUser.getPassword())) {
            return selectAdminUser;
        }else {
            return null;
        }
    }


    public CustomerUser checkCustomerAuthentication(CustomerUser customerUser){
        List<CustomerUser> existCustomerUser = customerRepository.findCustomerUserByUserName(customerUser.getUserName());

        if(existCustomerUser.isEmpty()){
            return null;
        }
        CustomerUser selectCustomerUser = existCustomerUser.get(0);

        if(selectCustomerUser.getPassword().equals(customerUser.getPassword())){
            return selectCustomerUser;
        }else {
            return null;
        }
    }
    
}
