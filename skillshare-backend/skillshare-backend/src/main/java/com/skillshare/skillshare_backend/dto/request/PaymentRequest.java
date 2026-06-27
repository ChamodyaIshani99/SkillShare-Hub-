package com.skillshare.skillshare_backend.dto.request;

import com.skillshare.skillshare_backend.enums.PaymentMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PaymentRequest {

    @NotBlank(message = "Course ID is required")
    private String courseId;

    @NotNull(message = "Payment method is required")
    private PaymentMethod paymentMethod;

}