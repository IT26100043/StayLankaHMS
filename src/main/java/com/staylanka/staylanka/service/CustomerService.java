package com.staylanka.staylanka.service;

import com.staylanka.staylanka.entity.CustomerUser;
import com.staylanka.staylanka.repository.AdminUserRepository;
import com.staylanka.staylanka.repository.CustomerRepository;
import com.staylanka.staylanka.entity.room.Booking;
import com.staylanka.staylanka.repository.Room.BookingRepository;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;


@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final AdminUserRepository adminUserRepository;
    private final BookingRepository bookingRepository;

    public CustomerService(CustomerRepository customerRepository, AdminUserRepository adminUserRepository,
                           BookingRepository bookingRepository){
        this.customerRepository = customerRepository;
        this.adminUserRepository = adminUserRepository;
        this.bookingRepository = bookingRepository;
    }

    public List<CustomerUser> getCustomers(){
        return customerRepository.findAll();
    }

    public Optional<CustomerUser> getCustomerById(Long id){
        return customerRepository.findById(id);
    }

    
    public CustomerUser UpdateCustomer(Long id, CustomerUser customerDetails ){
        CustomerUser existingUser = customerRepository.findById(id).orElseThrow();
        String oldUserName = existingUser.getUserName();
        String oldPhoneNumber = existingUser.getPhoneNumber();

        if(customerDetails.getUserName() != null){
            if (customerRepository.existsByUserNameAndIdNot(customerDetails.getUserName(), id)
                    || adminUserRepository.existsByUserName(customerDetails.getUserName())) {
                throw duplicateUserException();
            }
            existingUser.setUserName(customerDetails.getUserName());
        }

         if(customerDetails.getPassword() != null) {
            existingUser.setPassword(customerDetails.getPassword());
        }
        if(customerDetails.getPhoneNumber() != null){
            if (customerRepository.existsByPhoneNumberAndIdNot(customerDetails.getPhoneNumber(), id)
                    || adminUserRepository.existsByPhoneNumber(customerDetails.getPhoneNumber())) {
                throw duplicateUserException();
            }
            existingUser.setPhoneNumber(customerDetails.getPhoneNumber());
        }
        if(customerDetails.getSecurityNumber() != null){
            existingUser.setSecurityNumber(customerDetails.getSecurityNumber());
        }
        String newUserName = existingUser.getUserName();
        String newPhoneNumber = existingUser.getPhoneNumber();
        if (!java.util.Objects.equals(oldUserName, newUserName)
                || !java.util.Objects.equals(oldPhoneNumber, newPhoneNumber)) {
            bookingRepository.findByCustomerIdOrGuestPhone(id, oldPhoneNumber).forEach(booking -> {
                booking.setCustomerId(id);
                booking.setGuestName(newUserName);
                booking.setGuestPhone(newPhoneNumber);
                bookingRepository.save(booking);
            });
        }
        customerRepository.save(existingUser);
        return existingUser;

    }

    public void deleteCustomer(Long id){
        customerRepository.deleteById(id);
    }


    public CustomerUser registerCustomer(CustomerUser customerUser){
        if (customerRepository.existsByUserName(customerUser.getUserName())
                || customerRepository.existsByPhoneNumber(customerUser.getPhoneNumber())
                || adminUserRepository.existsByUserName(customerUser.getUserName())
                || adminUserRepository.existsByPhoneNumber(customerUser.getPhoneNumber())) {
            throw duplicateUserException();
        }
        return customerRepository.save(customerUser);
    }

    private ResponseStatusException duplicateUserException() {
        return new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Username or phone number already exists"
        );
    }
}
