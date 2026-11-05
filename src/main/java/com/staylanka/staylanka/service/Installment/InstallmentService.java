package com.staylanka.staylanka.service.Installment;

import com.staylanka.staylanka.entity.Installment.Installment;
import com.staylanka.staylanka.entity.room.Booking;
import com.staylanka.staylanka.repository.Installment.InstallmentRepository;
import com.staylanka.staylanka.repository.Room.BookingRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InstallmentService {

    private final InstallmentRepository installmentRepository;
    private final BookingRepository bookingRepository;

    public InstallmentService(
            InstallmentRepository installmentRepository,
            BookingRepository bookingRepository) {

        this.installmentRepository = installmentRepository;
        this.bookingRepository = bookingRepository;
    }

    public List<Installment> createInstallments(Long bookingId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() ->
                        new RuntimeException("Booking not found"));

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
}
