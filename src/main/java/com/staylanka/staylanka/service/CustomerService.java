package com.staylanka.staylanka.service;

import com.staylanka.staylanka.entity.CustomerUser;
import com.staylanka.staylanka.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    public List<CustomerUser> getCustomers(){
        return customerRepository.findAll();
    }

    public Optional<CustomerUser> getCustomerById(Long id){
        return customerRepository.findById(id);
    }

    public CustomerUser UpdateCustomer(Long id, CustomerUser customerDetails ){
        CustomerUser existingUser = customerRepository.findById(id).orElseThrow();

        if(customerDetails.getUserName() != null){
            existingUser.setUserName(customerDetails.getUserName());
        }

        if(customerDetails.getPassword() != null) {
            existingUser.setPassword(customerDetails.getPassword());
        }
        if(customerDetails.getPhoneNumber() != null){
            existingUser.setPhoneNumber(customerDetails.getPhoneNumber());
        }
        customerRepository.save(existingUser);
        return existingUser;

    }

    public void deleteCustomer(Long id){
        customerRepository.deleteById(id);
    }


    public CustomerUser registerCustomer(CustomerUser customerUser){
        return customerRepository.save(customerUser);
    }









}
