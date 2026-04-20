package com.smartsave.smartsave_backend.dto;

import java.math.BigDecimal;

public record DashboardResponse(
    String userName,
    long goalsCount,
    long transactionsCount,
    BigDecimal totalSaved,
    BigDecimal totalIncome,
    BigDecimal totalExpenses
) {
}
