package com.staylanka.staylanka.repository.payment;

import com.staylanka.staylanka.entity.payment.CardPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardPaymentRepository extends JpaRepository<CardPayment,Long> {
}
