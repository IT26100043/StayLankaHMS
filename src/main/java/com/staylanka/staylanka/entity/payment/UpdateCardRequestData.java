package com.staylanka.staylanka.entity.payment;

public class UpdateCardRequestData {
    private String cardHolder;
    private String cardCvvNumber;
    private String cardNumber;

    public UpdateCardRequestData(){}

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

    //setters

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
