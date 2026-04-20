package com.smartsave.smartsave_backend.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ExchangeRateResponse(
    Long id,
    String baseCurrency,
    String targetCurrency,
    BigDecimal rate,
    LocalDateTime fetchedAt,
    String source
) {
}
