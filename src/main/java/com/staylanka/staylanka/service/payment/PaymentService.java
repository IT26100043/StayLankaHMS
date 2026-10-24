package com.staylanka.staylanka.service.payment;

import com.staylanka.staylanka.entity.payment.payment;
import com.staylanka.staylanka.repository.payment.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository){
        this.paymentRepository = paymentRepository;
    }

    public List<payment> getAllPaymentDetails(){
        return paymentRepository.findAll();
    }
}
