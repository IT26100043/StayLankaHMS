package com.staylanka.staylanka.entity.payment;

public class UpdateCardRequestData {
    private String cardHolder;
    private String cardCvvNumber;

    public UpdateCardRequestData(){}

    //getters

    public String getCardCvvNumber() {
        return cardCvvNumber;
    }

    public String getCardHolder() {
        return cardHolder;
    }


    //setters

    public void setCardCvvNumber(String cardCvvNumber) {
        this.cardCvvNumber = cardCvvNumber;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }


}
