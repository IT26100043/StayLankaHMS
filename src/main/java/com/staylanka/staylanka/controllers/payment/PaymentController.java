package com.staylanka.staylanka.controllers.payment;

import org.springframework.web.bind.annotation.CrossOrigin;


import com.staylanka.staylanka.entity.payment.CardPayment;
import com.staylanka.staylanka.entity.payment.CardPaymentRequest;
import com.staylanka.staylanka.entity.payment.UpdateCardRequestData;
import com.staylanka.staylanka.entity.payment.payment;
import com.staylanka.staylanka.service.payment.PaymentService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/payment")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService){
        this.paymentService = paymentService;
    }

    //get all the payment
    @GetMapping("payments")
    public List<payment> getAllPaymentDetails(){
        return paymentService.getAllPaymentDetails();
    }

    // payment from id
    @GetMapping("/{id}")
    public Optional<payment> getPaymentById(@PathVariable Long id){
        return paymentService.getPaymentById(id);
    }

    @PostMapping("/newpay")
    public ResponseEntity<?> CreateNewPayment(@RequestBody CardPaymentRequest cardPaymentRequest){
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(paymentService.CreateNewPayment(cardPaymentRequest));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public payment UpdateNewPayment(@PathVariable Long id, @RequestBody UpdateCardRequestData updateCardRequestData){
        return paymentService.UpdatePayment(id,updateCardRequestData);
    }

    @DeleteMapping("/{id}")
    public void DeletePaymentById(@PathVariable Long id){
        paymentService.DeletePayment(id);
    }

}