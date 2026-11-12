
package com.staylanka.staylanka;

import com.staylanka.staylanka.entity.AdminUser;
import com.staylanka.staylanka.entity.CustomerUser;
import com.staylanka.staylanka.entity.payment.payment;
import com.staylanka.staylanka.entity.review.Review;
import com.staylanka.staylanka.entity.room.*;
import com.staylanka.staylanka.repository.AdminUserRepository;
import com.staylanka.staylanka.repository.CustomerRepository;
import com.staylanka.staylanka.repository.Room.BookingRepository;
import com.staylanka.staylanka.repository.Room.RoomRepository;
import com.staylanka.staylanka.repository.payment.PaymentRepository;
import com.staylanka.staylanka.repository.review.ReviewRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Configuration
    public class CustomerDataInitializer {

        @Bean
        CommandLineRunner initDatabase(CustomerRepository customerRepository , AdminUserRepository adminUserRepository, RoomRepository roomRepository, PaymentRepository paymentRepository, BookingRepository bookingRepository, ReviewRepository reviewRepository)  {
            return new CommandLineRunner() {
                @Override
                public void run(String... args) {
                    // add customer dummy data
                    customerRepository.save(new CustomerUser("Alex", "abc@123", "0712883221","500"));
                    customerRepository.save(new CustomerUser("Elon","xyz@789", "0712993167","600"));

                    //add admin dummy data
                    adminUserRepository.save(new AdminUser("Root","root","0712773206","600"));
                    //adminUserRepository.save(new AdminUser("Admin2","xyz@123","0711256789","700"));
                   //adminUserRepository.save(new AdminUser("Admin3","pqr@123","071145678","600"));

                    // add room dummy data
                    // Standard room
                    roomRepository.save(new StandedRoom("R101",5000,2));
                    roomRepository.save(new StandedRoom("R102",5000,2));
                    roomRepository.save(new StandedRoom("R103",5500,3));

                    // Deluxe room
                    roomRepository.save(new DeluxeRoom("R201",8500,2));
                    roomRepository.save(new DeluxeRoom("R202",9000,3));
                    roomRepository.save(new DeluxeRoom("R203",9500,4));

                    //Suites
                    roomRepository.save(new SuiteRoom("R301",15000,4));
                    roomRepository.save(new SuiteRoom("R302", 18000, 4, LocalDate.of(2026, 11, 5), LocalDate.of(2026, 11, 12)));

                    //paymentDummy
                    CustomerUser customerUser = new CustomerUser("MahindaRajapaksha","M1234","0712013269","800");
                    Booking booking = new Booking("R203","Namal","071233654", LocalDate.of(2026, 11, 1),LocalDate.of(2026,11,10),1500,"CONFIRMED");

                    bookingRepository.save(new Booking("R302","Mahinda Rajapaksha","0712013269", LocalDate.of(2026, 11, 1),LocalDate.of(2026,11,10),1500,"CONFIRMED"));

                    customerRepository.save(customerUser);
                    bookingRepository.save(booking);

                    paymentRepository.save(new payment(customerUser,booking,"CONFIRMED", LocalDateTime.of(2026,11,20,10,10,20),1000));

                    //review dummy
                    Room room1 = new StandedRoom("R105",5500,3);
                    roomRepository.save(room1);

                    reviewRepository.save(new Review(customerUser,room1,5,"Best Customer Service",LocalDate.of(2026,11,25)));
                }
            };
        }
    }
