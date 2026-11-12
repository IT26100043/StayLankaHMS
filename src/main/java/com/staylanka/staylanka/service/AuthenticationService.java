package com.staylanka.staylanka.service;

import com.staylanka.staylanka.entity.AdminUser;
import com.staylanka.staylanka.entity.CustomerUser;
import com.staylanka.staylanka.entity.RequestForgotPassword;
import com.staylanka.staylanka.entity.RequestUserDataByUserName;
import com.staylanka.staylanka.repository.AdminUserRepository;
import com.staylanka.staylanka.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationService {

    public final AdminUserRepository adminUserRepository;
    public final CustomerRepository customerRepository;
    public AuthenticationService(AdminUserRepository adminUserRepository,CustomerRepository customerRepository){
        this.adminUserRepository = adminUserRepository;
        this.customerRepository = customerRepository;
    }


    public AdminUser checkAdminAuthentication(RequestUserDataByUserName userLoginData) {
        List<AdminUser> existAdminUser = adminUserRepository.findAdminUserByUserName(userLoginData.getUserName());
        if(existAdminUser.isEmpty()){
            return null;
        }
        AdminUser selectAdminUser = existAdminUser.get(0);

        if(selectAdminUser.getPassword().equals(userLoginData.getPassword())) {
            return selectAdminUser;
        }else {
            return null;
        }
    }


    public CustomerUser checkCustomerAuthentication(RequestUserDataByUserName userLoginData){
        List<CustomerUser> existCustomerUser = customerRepository.findCustomerUserByUserName(userLoginData.getUserName());

        if(existCustomerUser.isEmpty()){
            return null;
        }
        CustomerUser selectCustomerUser = existCustomerUser.get(0);

        if(selectCustomerUser.getPassword().equals(userLoginData.getPassword())){
            return selectCustomerUser;
        }else {
            return null;
        }
    }


    public CustomerUser forgotPasswordCustomer(RequestForgotPassword requestForgotPassword){
        List<CustomerUser> existingCustomer = customerRepository.findCustomerUserByUserName(requestForgotPassword.getUserName());

        if(existingCustomer.isEmpty()){
            return null;
        }
        CustomerUser selectCustomerUser = existingCustomer.get(0);

        if(selectCustomerUser.getUserName().equals(requestForgotPassword.getUserName()) && selectCustomerUser.getPhoneNumber().equals(requestForgotPassword.getPhoneNumber()) && selectCustomerUser.getSecurityNumber().equals(requestForgotPassword.getSecurityNumber())){
            selectCustomerUser.setPassword(requestForgotPassword.getPassword());
            customerRepository.save(selectCustomerUser);
            return selectCustomerUser;
        }else return null;
    }


    public AdminUser forgotPasswordAdmin(RequestForgotPassword requestForgotPassword){
        List<AdminUser> existingAdmin = adminUserRepository.findAdminUserByUserName(requestForgotPassword.getUserName());

        if(existingAdmin.isEmpty()){
            return null;
        }
        AdminUser selectAdminUser = existingAdmin.get(0);

        if(selectAdminUser.getUserName().equals(requestForgotPassword.getUserName()) && selectAdminUser.getPhoneNumber().equals(requestForgotPassword.getPhoneNumber()) && selectAdminUser.getSecurityNumber().equals(requestForgotPassword.getSecurityNumber())){
            selectAdminUser.setPassword(requestForgotPassword.getPassword());
            adminUserRepository.save(selectAdminUser);
            return selectAdminUser;
        }else return null;
    }



    
}
