package com.staylanka.staylanka.entity.payment;

import com.staylanka.staylanka.entity.User;
import com.staylanka.staylanka.entity.room.Booking;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Payment")
public class payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    private double amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerId",nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookingId",nullable = false)
    private Booking booking;

    private String status;
    private LocalDateTime paymentDate;




    public payment(){}

    public payment(User user,Booking booking,String status,LocalDateTime paymentDate,double amount){
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.booking = booking;
        this.status = status;
        this.user  = user;

    }


    //getter

    public Long getPaymentId() {
        return paymentId;
    }

    public double getAmount() {
        return amount;
    }

    public Booking getBooking() {
        return booking;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public String getStatus() {
        return status;
    }

    public User getUser() {
        return user;
    }

    //setter

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
