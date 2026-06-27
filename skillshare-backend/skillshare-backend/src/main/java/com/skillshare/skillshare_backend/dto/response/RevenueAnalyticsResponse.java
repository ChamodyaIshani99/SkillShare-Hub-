package com.skillshare.skillshare_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RevenueAnalyticsResponse {

    // Revenue

    private Double todayRevenue;

    private Double weeklyRevenue;

    private Double monthlyRevenue;

    private Double yearlyRevenue;

    private Double totalRevenue;

    // Payments

    private Long todayPayments;

    private Long weeklyPayments;

    private Long monthlyPayments;

    private Long yearlyPayments;

    private Long totalPayments;

    // Refunds

    private Double refundedAmount;

    private Long refundedPayments;

}