
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
                repository.save(new CustomerUser("testuser1", "abc@123", "07126886299"));
                repository.save(new CustomerUser("testuser2","xyz@789", "07112345678"));
                adminUserRepository.save(new AdminUser("Admin1","abcd@123","01188123488"));
                adminUserRepository.save(new AdminUser("Admin2","xyz@123","071123456789"));
                adminUserRepository.save(new AdminUser("Admin3","pqr@123","07112345678"));
            };
        }
    }
