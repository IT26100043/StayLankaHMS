package com.staylanka.staylanka.entity.Installment;

import com.staylanka.staylanka.entity.room.Booking;
import jakarta.persistence.*;

@Entity
public class Installment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long installmentId;

    private int installmentNumber;

    private double amount;

    private String status;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    //getters


    public double getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }

    public int getInstallmentNumber() {
        return installmentNumber;
    }

    public Long getInstallmentId() {
        return installmentId;
    }

    public Booking getBooking() {
        return booking;
    }

    //setter

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setInstallmentId(Long installmentId) {
        this.installmentId = installmentId;
    }

    public void setInstallmentNumber(int installmentNumber) {
        this.installmentNumber = installmentNumber;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }
}

