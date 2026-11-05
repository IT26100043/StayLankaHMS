package com.staylanka.staylanka.entity.Installment;

public class InstallmentPaymentRequest {
    private Long customerId;
    private String cardCvvNumber;
    private String cardHolderName;
    private String cardNumber;

    public InstallmentPaymentRequest() {
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

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
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
