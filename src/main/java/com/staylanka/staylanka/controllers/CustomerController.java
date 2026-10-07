package com.staylanka.staylanka.controllers;

import com.staylanka.staylanka.entity.CustomerUser;
import com.staylanka.staylanka.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/customer")
public class CustomerController {

    public final CustomerService customerService;

    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

    @GetMapping("/customers")
    public List<CustomerUser> getCustomers(){
        return customerService.getCustomers();
    }

    @GetMapping("/{id}")
    public Optional<CustomerUser> getCustomerById(@PathVariable Long id){
        return customerService.getCustomerById(id);
    }

    @PostMapping("/register")
    public ResponseEntity<CustomerUser> registerCustomer(@RequestBody CustomerUser customerUser){
        CustomerUser newUser = customerService.registerCustomer(customerUser);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public CustomerUser updateCustomer(@PathVariable Long id,@RequestBody CustomerUser customerUser){
        return customerService.UpdateCustomer(id,customerUser);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id){
        customerService.deleteCustomer(id);
    }

}
