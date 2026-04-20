package com.smartsave.smartsave_backend.dto;

import com.smartsave.smartsave_backend.domain.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record TransactionResponse(
    Long id,
    TransactionType type,
    BigDecimal amount,
    String description,
    LocalDate transactionDate,
    List<String> tags
) {
}
