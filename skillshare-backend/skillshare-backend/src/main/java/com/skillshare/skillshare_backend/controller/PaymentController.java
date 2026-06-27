package com.skillshare.skillshare_backend.controller;

import com.skillshare.skillshare_backend.dto.request.PaymentRequest;
import com.skillshare.skillshare_backend.dto.response.ApiResponse;
import com.skillshare.skillshare_backend.dto.response.PaymentResponse;
import com.skillshare.skillshare_backend.enums.PaymentStatus;
import com.skillshare.skillshare_backend.service.interfaces.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PaymentController {

    private final PaymentService paymentService;

    // ==========================================
    // Student APIs
    // ==========================================

    @PostMapping
    public ResponseEntity<ApiResponse<PaymentResponse>> makePayment(
            @Valid @RequestBody PaymentRequest request) {

        PaymentResponse response = paymentService.makePayment(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<PaymentResponse>builder()
                        .success(true)
                        .message("Payment completed successfully")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @GetMapping("/my-payments")
    public ResponseEntity<ApiResponse<List<PaymentResponse>>> getMyPayments() {

        List<PaymentResponse> response = paymentService.getMyPayments();

        return ResponseEntity.ok(
                ApiResponse.<List<PaymentResponse>>builder()
                        .success(true)
                        .message("Payments retrieved successfully")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PaymentResponse>> getPaymentById(
            @PathVariable String id) {

        PaymentResponse response = paymentService.getById(id);

        return ResponseEntity.ok(
                ApiResponse.<PaymentResponse>builder()
                        .success(true)
                        .message("Payment retrieved successfully")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    // ==========================================
    // Admin APIs
    // ==========================================

    @GetMapping
    public ResponseEntity<ApiResponse<List<PaymentResponse>>> getAllPayments() {

        List<PaymentResponse> response = paymentService.getAllPayments();

        return ResponseEntity.ok(
                ApiResponse.<List<PaymentResponse>>builder()
                        .success(true)
                        .message("All payments retrieved successfully")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ApiResponse<PaymentResponse>> updatePaymentStatus(
            @PathVariable String id,
            @RequestParam PaymentStatus status) {

        PaymentResponse response =
                paymentService.updatePaymentStatus(id, status);

        return ResponseEntity.ok(
                ApiResponse.<PaymentResponse>builder()
                        .success(true)
                        .message("Payment status updated successfully")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @PutMapping("/{id}/refund")
    public ResponseEntity<ApiResponse<PaymentResponse>> refundPayment(
            @PathVariable String id) {

        PaymentResponse response =
                paymentService.refundPayment(id);

        return ResponseEntity.ok(
                ApiResponse.<PaymentResponse>builder()
                        .success(true)
                        .message("Payment refunded successfully")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

}