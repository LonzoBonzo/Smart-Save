package com.smartsave.smartsave_backend.dto;

import com.smartsave.smartsave_backend.domain.GoalStatus;
import java.math.BigDecimal;
import java.time.LocalDate;

public record SavingsGoalResponse(
    Long id,
    String name,
    BigDecimal targetAmount,
    BigDecimal currentAmount,
    GoalStatus status,
    LocalDate targetDate
) {
}
