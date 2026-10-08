package com.staylanka.staylanka.service;

import com.staylanka.staylanka.entity.AdminUser;
import com.staylanka.staylanka.repository.AdminUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private final AdminUserRepository adminUserRepository;

    public AdminService(AdminUserRepository adminUserRepository){
        this.adminUserRepository = adminUserRepository;
    }

    public List<AdminUser> getAdminUser(){
        return adminUserRepository.findAll();
    }


}
