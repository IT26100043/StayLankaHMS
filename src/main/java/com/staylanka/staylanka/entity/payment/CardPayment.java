package com.staylanka.staylanka.entity.payment;


import com.staylanka.staylanka.entity.CustomerUser;
import com.staylanka.staylanka.entity.User;
import com.staylanka.staylanka.entity.room.Booking;
import jakarta.persistence.Entity;

import java.time.LocalDateTime;
import java.util.Optional;

@Entity
public class CardPayment extends payment{

    private String cardHolder;
    private String cardCvvNumber;

    public CardPayment(String cardHolder, String cardCvvNumber, User user, Booking booking, String status, LocalDateTime paymentDate, double amount){
        super(user,booking,status,paymentDate,amount);
        this.cardCvvNumber = cardCvvNumber;
        this.cardHolder = cardHolder;
    }

    public CardPayment() {}
}
