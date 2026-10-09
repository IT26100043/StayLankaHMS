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


}
