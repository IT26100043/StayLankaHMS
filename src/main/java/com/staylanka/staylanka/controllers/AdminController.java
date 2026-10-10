package com.staylanka.staylanka.controllers;

import com.staylanka.staylanka.entity.AdminUser;
import com.staylanka.staylanka.service.AdminService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController {

    public final AdminService adminService;

    public AdminController(AdminService adminService){
        this.adminService = adminService;
    }


    @GetMapping("/admins")
    public List<AdminUser> getAdminUser(){
        return adminService.getAdminUser();
    }


    @GetMapping("/{id}")
    public Optional<AdminUser> getAdminById(@PathVariable Long id){
        return adminService.getAdminById(id);
    }

    @PutMapping("/{id}")
    public AdminUser updateAdminUser(@PathVariable Long id,@RequestBody AdminUser adminUser){
        return adminService.updateAminUser(id,adminUser);
    }

    @DeleteMapping("/{id}")
    public void deleteAdminById(@PathVariable Long id){
        adminService.deleteAdminUser(id);
    }

    



}
