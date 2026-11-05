package com.staylanka.staylanka.service.payment;

import com.staylanka.staylanka.entity.CustomerUser;
import com.staylanka.staylanka.entity.payment.CardPayment;
import com.staylanka.staylanka.entity.payment.CardPaymentRequest;
import com.staylanka.staylanka.entity.payment.UpdateCardRequestData;
import com.staylanka.staylanka.entity.payment.payment;
import com.staylanka.staylanka.entity.room.Booking;
import com.staylanka.staylanka.repository.CustomerRepository;
import com.staylanka.staylanka.repository.Room.BookingRepository;
import com.staylanka.staylanka.repository.payment.CardPaymentRepository;
import com.staylanka.staylanka.repository.payment.PaymentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final CustomerRepository customerRepository;
    private final BookingRepository bookingRepository;
    private final CardPaymentRepository cardPaymentRepository;

    public PaymentService(PaymentRepository paymentRepository, CustomerRepository customerRepository, BookingRepository bookingRepository, CardPaymentRepository cardPaymentRepository){
        this.paymentRepository = paymentRepository;
        this.customerRepository = customerRepository;
        this.bookingRepository = bookingRepository;
        this.cardPaymentRepository = cardPaymentRepository;
    }

    public List<payment> getAllPaymentDetails(){
        return paymentRepository.findAll();
    }

    public Optional<payment> getPaymentById(Long id){
        return paymentRepository.findById(id);
    }

    public payment CreateNewPayment(CardPaymentRequest cardPaymentRequest){
        if (cardPaymentRequest == null
                || cardPaymentRequest.getCustomerId() == null
                || cardPaymentRequest.getBookingId() == null) {
            throw new IllegalArgumentException("Customer ID and booking ID are required");
        }
        CustomerUser customerUser = customerRepository.findById(cardPaymentRequest.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Customer " + cardPaymentRequest.getCustomerId() + " was not found"));
        Booking booking = bookingRepository.findById(cardPaymentRequest.getBookingId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Booking " + cardPaymentRequest.getBookingId() + " was not found"));
        if (cardPaymentRequest.getCardHolderName() == null
                || cardPaymentRequest.getCardHolderName().isBlank()
                || cardPaymentRequest.getCardNumber() == null
                || cardPaymentRequest.getCardNumber().isBlank()
                || cardPaymentRequest.getCardCvvNumber() == null
                || cardPaymentRequest.getCardCvvNumber().isBlank()) {
            throw new IllegalArgumentException("Card holder name, card number, and CVV are required");
        }
        CardPayment cardPayment = new CardPayment(cardPaymentRequest.getCardHolderName(), cardPaymentRequest.getCardCvvNumber(), customerUser,booking,"PENDING", LocalDateTime.now(),booking.getTotalPrice(),cardPaymentRequest.getCardNumber());
        return paymentRepository.save(cardPayment);
    }

    public CardPayment UpdatePayment(Long id, UpdateCardRequestData updateCardRequestData){
        CardPayment existingPayment = cardPaymentRepository.findById(id).orElseThrow();

        if(updateCardRequestData.getCardCvvNumber() != null){
            existingPayment.setCardCvvNumber(updateCardRequestData.getCardCvvNumber());
        }
        if(updateCardRequestData.getCardHolder() != null){
            existingPayment.setCardHolder(updateCardRequestData.getCardHolder());
        }
        if(updateCardRequestData.getCardNumber() != null){
            existingPayment.setCardNumber(updateCardRequestData.getCardNumber());
        }
        return paymentRepository.save(existingPayment);
    }

    public void DeletePayment(Long id){
        paymentRepository.deleteById(id);
        System.out.println("Delete Successful");
    }
}
