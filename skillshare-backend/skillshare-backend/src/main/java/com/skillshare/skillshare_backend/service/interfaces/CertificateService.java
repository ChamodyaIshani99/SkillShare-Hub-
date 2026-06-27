package com.skillshare.skillshare_backend.service.interfaces;

import com.skillshare.skillshare_backend.dto.request.CertificateRequest;
import com.skillshare.skillshare_backend.dto.response.CertificateResponse;

import java.util.List;

public interface CertificateService {

    // ==========================================
    // Student APIs
    // ==========================================

    // Generate certificate
    CertificateResponse generate(CertificateRequest request);

    // Get my certificates
    List<CertificateResponse> getMyCertificates();

    // ==========================================
    // Public APIs
    // ==========================================

    // Get certificate by ID
    CertificateResponse getById(String certificateId);

    // Verify certificate
    CertificateResponse verifyCertificate(String certificateNumber);

    // ==========================================
    // Admin APIs
    // ==========================================

    // Get all certificates
    List<CertificateResponse> getAllCertificates();

}