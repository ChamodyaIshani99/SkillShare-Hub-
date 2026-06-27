package com.skillshare.skillshare_backend.entity;

import com.skillshare.skillshare_backend.enums.PaymentMethod;
import com.skillshare.skillshare_backend.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "payments")
public class Payment {

    @Id
    private String id;

    @DBRef
    private User student;

    @DBRef
    private Course course;

    private Double amount;

    @Builder.Default
    private String currency = "LKR";

    private PaymentMethod paymentMethod;

    private String transactionId;

    @Builder.Default
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;

    @Builder.Default
    private LocalDateTime paymentDate = LocalDateTime.now();

}