package com.staylanka.staylanka.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "payment")
public class payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    private double amount;
    private String paymentType;
    private String cvn;


    public payment(){}

    public payment(double amount,String paymentType){
        this.amount = amount;
        this.paymentType = paymentType;
    }

    //getters
    public Long getPaymentId() {
        return paymentId;
    }

    public double getAmount() {
        return amount;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public String getCvn() {
        return cvn;
    }

    //setters
    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public void setCvn(String cvn) {
        this.cvn = cvn;
    }
}
