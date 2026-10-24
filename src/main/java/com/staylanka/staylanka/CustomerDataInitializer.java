
package com.staylanka.staylanka;

import com.staylanka.staylanka.entity.AdminUser;
import com.staylanka.staylanka.entity.CustomerUser;
import com.staylanka.staylanka.entity.payment.payment;
import com.staylanka.staylanka.entity.room.Booking;
import com.staylanka.staylanka.entity.room.DeluxeRoom;
import com.staylanka.staylanka.entity.room.StandedRoom;
import com.staylanka.staylanka.entity.room.SuiteRoom;
import com.staylanka.staylanka.repository.AdminUserRepository;
import com.staylanka.staylanka.repository.CustomerRepository;
import com.staylanka.staylanka.repository.Room.BookingRepository;
import com.staylanka.staylanka.repository.Room.RoomRepository;
import com.staylanka.staylanka.repository.payment.PaymentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Configuration
    public class CustomerDataInitializer {

        @Bean
        CommandLineRunner initDatabase(CustomerRepository customerRepository , AdminUserRepository adminUserRepository, RoomRepository roomRepository, PaymentRepository paymentRepository, BookingRepository bookingRepository)  {
            return args -> {
                // add customer dummy data
                customerRepository.save(new CustomerUser("testuser1", "abc@123", "07126886299"));
                customerRepository.save(new CustomerUser("testuser2","xyz@789", "07112345678"));

                //add admin dummy data
                adminUserRepository.save(new AdminUser("Admin1","abcd@123","01188123488"));
                adminUserRepository.save(new AdminUser("Admin2","xyz@123","071123456789"));
                adminUserRepository.save(new AdminUser("Admin3","pqr@123","07112345678"));

                // add room dummy data
                // Standard room
                roomRepository.save(new StandedRoom("R101",5000,2,"Available"));
                roomRepository.save(new StandedRoom("R102",5000,2,"Booked"));
                roomRepository.save(new StandedRoom("R103",5500,3,"Available"));

                // Deluxe room
                roomRepository.save(new DeluxeRoom("R201",8500,2,"Available"));
                roomRepository.save(new DeluxeRoom("R202",9000,3,"Booked"));
                roomRepository.save(new DeluxeRoom("R203",9500,4,"Available"));

                //Suites
                roomRepository.save(new SuiteRoom("R301",15000,4,"Available"));
                roomRepository.save(new SuiteRoom("R302", 18000, 4, "Maintenance"));

                //paymentDummy
                CustomerUser customerUser = new CustomerUser("lesly","Lesly123","078315468");
                Booking booking = new Booking("AAA","ABC","071233654", LocalDate.of(2026, 11, 1),LocalDate.of(2026,11,10),1500,"CONFIRMED");

                bookingRepository.save(new Booking("AAAs","ABC","071233654", LocalDate.of(2026, 11, 1),LocalDate.of(2026,11,10),1500,"CONFIRMED"));

                customerRepository.save(customerUser);
                bookingRepository.save(booking);

                paymentRepository.save(new payment(customerUser,booking,"CONFIRMED", LocalDateTime.of(2026,11,20,10,10,20),1000));

            };
        }
    }
