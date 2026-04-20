package com.smartsave.smartsave_backend.dto;

import com.smartsave.smartsave_backend.domain.TransactionType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record TransactionRequest(
    @NotNull(message = "Transaction type is required")
    TransactionType type,
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than zero")
    BigDecimal amount,
    @NotBlank(message = "Description is required")
    String description,
    @NotNull(message = "Transaction date is required")
    LocalDate transactionDate,
    @NotEmpty(message = "At least one tag is required")
    List<String> tags
) {
}
