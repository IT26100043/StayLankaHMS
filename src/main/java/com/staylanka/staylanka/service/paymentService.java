package com.staylanka.staylanka.service;

import com.staylanka.staylanka.entity.payment;
import com.staylanka.staylanka.repository.PaymentRepository;
import org.hibernate.mapping.List;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class paymentService {
    public final PaymentRepository paymentRepository;

    public paymentService(PaymentRepository paymentRepository){
        this.paymentRepository = paymentRepository;
    }
    

}

