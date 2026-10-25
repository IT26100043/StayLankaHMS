
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
            return args -> {
                // add customer dummy data
                customerRepository.save(new CustomerUser("testuser1", "abc@123", "0712655299","500"));
                customerRepository.save(new CustomerUser("testuser2","xyz@789", "071345678","600"));

                //add admin dummy data
                adminUserRepository.save(new AdminUser("Admin1","abcd@123","94118812388","600"));
                adminUserRepository.save(new AdminUser("Admin2","xyz@123","0711256789","700"));
                adminUserRepository.save(new AdminUser("Admin3","pqr@123","071145678","600"));

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
                CustomerUser customerUser = new CustomerUser("lesly","Lesly123","078300468","800");
                Booking booking = new Booking("AAA","ABC","071233654", LocalDate.of(2026, 11, 1),LocalDate.of(2026,11,10),1500,"CONFIRMED");

                bookingRepository.save(new Booking("AAAs","ABC","07123888854", LocalDate.of(2026, 11, 1),LocalDate.of(2026,11,10),1500,"CONFIRMED"));

                customerRepository.save(customerUser);
                bookingRepository.save(booking);

                paymentRepository.save(new payment(customerUser,booking,"CONFIRMED", LocalDateTime.of(2026,11,20,10,10,20),1000));

                //review dummy
                Room room1 = new StandedRoom("R105",5500,3);
                roomRepository.save(room1);

                reviewRepository.save(new Review(customerUser,room1,5,"Good Rooms",LocalDate.of(2026,11,25)));
            };
        }
    }
