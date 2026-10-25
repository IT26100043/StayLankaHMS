package com.staylanka.staylanka.repository.payment;

import com.staylanka.staylanka.entity.payment.payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface PaymentRepository extends JpaRepository<payment,Long> {

    // number of paid, non-cancelled bookings of this room by this customer whose check-in day has arrived
    @Query("select count(p) from payment p where p.user.id = :customerId and p.booking.roomId = :roomId "
            + "and p.booking.checkIn <= :today and upper(p.booking.status) <> 'CANCELLED' "
            + "and upper(p.status) not in ('FAILED', 'CANCELLED', 'REFUNDED')")
    long countCheckedInPaidStays(@Param("customerId") Long customerId,
                                 @Param("roomId") String roomId,
                                 @Param("today") LocalDate today);
}
