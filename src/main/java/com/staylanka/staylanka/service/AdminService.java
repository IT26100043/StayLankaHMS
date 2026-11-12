package com.staylanka.staylanka.service;

import com.staylanka.staylanka.entity.AdminUser;
import com.staylanka.staylanka.repository.AdminUserRepository;
import com.staylanka.staylanka.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    private final AdminUserRepository adminUserRepository;
    private final CustomerRepository customerRepository;

    public AdminService(AdminUserRepository adminUserRepository, CustomerRepository customerRepository){
        this.adminUserRepository = adminUserRepository;
        this.customerRepository = customerRepository;
    }

    public List<AdminUser> getAdminUser(){
        return adminUserRepository.findAll();
    }

    public Optional<AdminUser> getAdminById(Long id){
        return adminUserRepository.findById(id);
    }


    public AdminUser updateAminUser(Long id,AdminUser adminUser){
        AdminUser existingUser = adminUserRepository.findById(id).orElseThrow();
        if (adminUser.getUserName() != null){
            if (adminUserRepository.existsByUserNameAndIdNot(adminUser.getUserName(), id)
                    || customerRepository.existsByUserName(adminUser.getUserName())) {
                throw duplicateUserException();
            }
            existingUser.setUserName(adminUser.getUserName());
        }
        if (adminUser.getPassword() != null){
            existingUser.setPassword(adminUser.getPassword());
        }
        if (adminUser.getPhoneNumber() != null){
            if (adminUserRepository.existsByPhoneNumberAndIdNot(adminUser.getPhoneNumber(), id)
                    || customerRepository.existsByPhoneNumber(adminUser.getPhoneNumber())) {
                throw duplicateUserException();
            }
            existingUser.setPhoneNumber(adminUser.getPhoneNumber());
        }
        if (adminUser.getSecurityNumber() != null){
            existingUser.setSecurityNumber(adminUser.getSecurityNumber());
        }
        return adminUserRepository.save(existingUser);
    }

    public void deleteAdminUser(Long id){
        adminUserRepository.deleteById(id);
    }

    public AdminUser registerNewUser(AdminUser adminUser){
        if (adminUserRepository.existsByUserName(adminUser.getUserName())
                || adminUserRepository.existsByPhoneNumber(adminUser.getPhoneNumber())
                || customerRepository.existsByUserName(adminUser.getUserName())
                || customerRepository.existsByPhoneNumber(adminUser.getPhoneNumber())) {
            throw duplicateUserException();
        }
        return adminUserRepository.save(adminUser);

    }

    private ResponseStatusException duplicateUserException() {
        return new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Username or phone number already exists"
        );
    }

}
