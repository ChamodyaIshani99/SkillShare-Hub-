package com.skillshare.skillshare_backend.service.interfaces;

import com.skillshare.skillshare_backend.dto.request.PaymentRequest;
import com.skillshare.skillshare_backend.dto.response.PaymentResponse;
import com.skillshare.skillshare_backend.enums.PaymentStatus;

import java.util.List;

public interface PaymentService {

    // ==========================================
    // Student APIs
    // ==========================================

    // Make payment
    PaymentResponse makePayment(PaymentRequest request);

    // Get my payments
    List<PaymentResponse> getMyPayments();

    // Get payment by ID
    PaymentResponse getById(String paymentId);

    // ==========================================
    // Admin APIs
    // ==========================================

    // Get all payments
    List<PaymentResponse> getAllPayments();

    // Update payment status
    PaymentResponse updatePaymentStatus(
            String paymentId,
            PaymentStatus status
    );

    // Refund payment
    PaymentResponse refundPayment(String paymentId);

}