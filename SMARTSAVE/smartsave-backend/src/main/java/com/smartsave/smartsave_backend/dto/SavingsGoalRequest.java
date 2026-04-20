package com.smartsave.smartsave_backend.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public record SavingsGoalRequest(
    @NotBlank(message = "Goal name is required")
    String name,
    @NotNull(message = "Target amount is required")
    @DecimalMin(value = "0.01", message = "Target amount must be greater than zero")
    BigDecimal targetAmount,
    @DecimalMin(value = "0.00", message = "Current amount cannot be negative")
    BigDecimal currentAmount,
    LocalDate targetDate
) {
}
