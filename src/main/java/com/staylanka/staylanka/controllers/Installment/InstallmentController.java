package com.staylanka.staylanka.controllers.Installment;

import com.staylanka.staylanka.entity.Installment.Installment;
import com.staylanka.staylanka.service.Installment.InstallmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}