package com.staylanka.staylanka.repository.Installment;

import com.staylanka.staylanka.entity.Installment.Installment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstallmentRepository
        extends JpaRepository<Installment, Long> {

    List<Installment> findByBooking_BookingId(Long bookingId);
}
