package com.staylanka.staylanka.entity.payment;

public class CardPaymentRequest {
    private Long customerId;
    private Long bookingId;
    private String cardCvvNumber;
    private String cardHolderName;
    private String cardNumber;

    public CardPaymentRequest(){}

    //getters

    public Long getBookingId() {
        return bookingId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public String getCardCvvNumber() {
        return cardCvvNumber;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    //setters

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public void setCardCvvNumber(String cardCvvNumber) {
        this.cardCvvNumber = cardCvvNumber;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
}
