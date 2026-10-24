package com.staylanka.staylanka.entity.payment;

import com.staylanka.staylanka.entity.User;
import jakarta.persistence.*;

@Entity
public class payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    private double amount;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerId",nullable = false)
    private User user;


    //getter

    public Long getPaymentId() {
        return paymentId;
    }

    public double getAmount() {
        return amount;
    }



    //setter

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }


}
