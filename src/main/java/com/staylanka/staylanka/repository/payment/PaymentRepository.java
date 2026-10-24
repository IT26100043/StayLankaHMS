package com.staylanka.staylanka.repository.payment;

import com.staylanka.staylanka.entity.payment.payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<payment,Long> {
}
