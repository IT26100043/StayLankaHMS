package com.staylanka.staylanka.controllers.Installment;

import com.staylanka.staylanka.entity.Installment.Installment;
import com.staylanka.staylanka.entity.Installment.InstallmentPaymentRequest;
import com.staylanka.staylanka.service.Installment.InstallmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/installments")
public class InstallmentController {

    private final InstallmentService installmentService;

    public InstallmentController(
            InstallmentService installmentService) {
        this.installmentService = installmentService;
    }

    @PostMapping("/{bookingId}")
    public ResponseEntity<List<Installment>> createInstallments(
            @PathVariable Long bookingId) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(installmentService.createInstallments(bookingId));
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<List<Installment>> getInstallments(
            @PathVariable Long bookingId) {
        return ResponseEntity.ok(installmentService.getInstallments(bookingId));
    }

    @PostMapping("/{installmentId}/pay")
    public ResponseEntity<?> payInstallment(
            @PathVariable Long installmentId,
            @RequestBody InstallmentPaymentRequest request) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(installmentService.payInstallment(installmentId, request));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}