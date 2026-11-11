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
    private String cardNumber;

    public CardPayment(String cardHolder, String cardCvvNumber, User user, Booking booking, String status, LocalDateTime paymentDate, double amount , String cardNumber){
        super(user,booking,status,paymentDate,amount);
        this.cardCvvNumber = cardCvvNumber;
        this.cardHolder = cardHolder;
        this.cardNumber = cardNumber;
    }

    public CardPayment() {}


    //getters

    public String getCardCvvNumber() {
        return cardCvvNumber;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    //setter


    public void setCardCvvNumber(String cardCvvNumber) {
        this.cardCvvNumber = cardCvvNumber;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
}
