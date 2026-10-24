package com.staylanka.staylanka.controllers.payment;


import com.staylanka.staylanka.entity.payment.payment;
import com.staylanka.staylanka.service.payment.PaymentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService){
        this.paymentService = paymentService;
    }

    @GetMapping("payments")
    public List<payment> getAllPaymentDetails(){
        return paymentService.getAllPaymentDetails();
    }
}
