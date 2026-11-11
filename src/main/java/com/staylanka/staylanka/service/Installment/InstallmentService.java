package com.staylanka.staylanka.service.Installment;

import com.staylanka.staylanka.entity.Installment.Installment;
import com.staylanka.staylanka.entity.Installment.InstallmentPaymentRequest;
import com.staylanka.staylanka.entity.CustomerUser;
import com.staylanka.staylanka.entity.payment.CardPayment;
import com.staylanka.staylanka.entity.room.Booking;
import com.staylanka.staylanka.repository.CustomerRepository;
import com.staylanka.staylanka.repository.Installment.InstallmentRepository;
import com.staylanka.staylanka.repository.Room.BookingRepository;
import com.staylanka.staylanka.repository.payment.PaymentRepository;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InstallmentService {

    private final InstallmentRepository installmentRepository;
    private final BookingRepository bookingRepository;
    private final CustomerRepository customerRepository;
    private final PaymentRepository paymentRepository;

    public InstallmentService(
            InstallmentRepository installmentRepository,
            BookingRepository bookingRepository,
            CustomerRepository customerRepository,
            PaymentRepository paymentRepository) {

        this.installmentRepository = installmentRepository;
        this.bookingRepository = bookingRepository;
        this.customerRepository = customerRepository;
        this.paymentRepository = paymentRepository;
    }

    public List<Installment> createInstallments(Long bookingId) {

        Optional<Booking> bookingResult = bookingRepository.findById(bookingId);
        if (!bookingResult.isPresent()) {
            throw new RuntimeException("Booking not found");
        }
        Booking booking = bookingResult.get();

        if (!installmentRepository
                .findByBooking_BookingId(bookingId).isEmpty()) {
            throw new RuntimeException("Installments already exist");
        }

        double totalPrice = booking.getTotalPrice();

        double installmentAmount = Math.floor(totalPrice / 3 * 100) / 100;

        List<Installment> installments = new ArrayList<>();

        for (int i = 1; i <= 3; i++) {

            Installment installment = new Installment();

            installment.setInstallmentNumber(i);

            double amount = (i == 3)
                    ? Math.round((totalPrice - installmentAmount * 2) * 100) / 100.0
                    : installmentAmount;

            installment.setAmount(amount);
            installment.setStatus("PENDING");
            installment.setBooking(booking);

            installments.add(installment);
        }

        return installmentRepository.saveAll(installments);
    }

    public List<Installment> getInstallments(Long bookingId) {
        return installmentRepository.findByBooking_BookingId(bookingId);
    }

    @Transactional
    public CardPayment payInstallment(Long installmentId, InstallmentPaymentRequest request) {
        if (request == null || request.getCustomerId() == null
                || request.getCardHolderName() == null || request.getCardHolderName().isBlank()
                || request.getCardNumber() == null || request.getCardNumber().isBlank()
                || request.getCardCvvNumber() == null || request.getCardCvvNumber().isBlank()) {
            throw new IllegalArgumentException("Customer ID and complete card details are required");
        }
        Optional<Installment> installmentResult = installmentRepository.findById(installmentId);
        if (!installmentResult.isPresent()) {
            throw new IllegalArgumentException("Installment not found");
        }
        Installment installment = installmentResult.get();
        if (!"PENDING".equalsIgnoreCase(installment.getStatus())) {
            throw new IllegalArgumentException("This installment has already been paid");
        }
        Optional<CustomerUser> customerResult = customerRepository.findById(request.getCustomerId());
        if (!customerResult.isPresent()) {
            throw new IllegalArgumentException("Customer not found");
        }
        CustomerUser customer = customerResult.get();
        Booking booking = installment.getBooking();
        if (booking == null) {
            throw new IllegalArgumentException("Installment booking not found");
        }
        CardPayment payment = new CardPayment(request.getCardHolderName(), request.getCardCvvNumber(),
                customer, booking, "CONFIRMED", LocalDateTime.now(), installment.getAmount(), request.getCardNumber());
        installment.setStatus("PAID");
        installmentRepository.save(installment);
        return paymentRepository.save(payment);
    }
}
