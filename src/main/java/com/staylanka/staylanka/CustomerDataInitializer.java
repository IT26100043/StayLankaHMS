

package com.staylanka.staylanka;

import com.staylanka.staylanka.entity.AdminUser;
import com.staylanka.staylanka.entity.CustomerUser;
import com.staylanka.staylanka.repository.AdminUserRepository;
import com.staylanka.staylanka.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



    @Configuration
    public class CustomerDataInitializer {

        @Bean
        CommandLineRunner initDatabase(CustomerRepository repository , AdminUserRepository adminUserRepository) {
            return args -> {
                repository.save(new CustomerUser("testuser1", "abc@123", "0712773255"));
                repository.save(new CustomerUser("testuser2","xyz@789", "0712773255"));
                adminUserRepository.save(new AdminUser("Admin1","abcd@123","0778123488"));
            };
        }
    }
