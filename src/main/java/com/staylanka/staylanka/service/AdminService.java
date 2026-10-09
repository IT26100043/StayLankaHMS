package com.staylanka.staylanka.service;

import com.staylanka.staylanka.entity.AdminUser;
import com.staylanka.staylanka.repository.AdminUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    private final AdminUserRepository adminUserRepository;

    public AdminService(AdminUserRepository adminUserRepository){
        this.adminUserRepository = adminUserRepository;
    }

    public List<AdminUser> getAdminUser(){
        return adminUserRepository.findAll();
    }

    public Optional<AdminUser> getAdminById(Long id){
        return adminUserRepository.findById(id);
    }


    public AdminUser updateAminUser(long id,AdminUser adminUser){
        AdminUser existingUser = adminUserRepository.findById(id).orElseThrow();
        if (adminUser.getUserName() != null){
            existingUser.setUserName(adminUser.getUserName());
        }
        if (adminUser.getPassword() != null){
            existingUser.setPassword(adminUser.getPassword());
        }
        if (adminUser.getPhoneNumber() != null){
            existingUser.setPhoneNumber(adminUser.getPhoneNumber());
        }
        return adminUserRepository.save(existingUser);


    }

}
