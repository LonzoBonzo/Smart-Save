package com.smartsave.smartsave_backend.dto;

import java.util.List;

public record RateRefreshResponse(
    String status,
    String message,
    List<ExchangeRateResponse> rates
) {
}
