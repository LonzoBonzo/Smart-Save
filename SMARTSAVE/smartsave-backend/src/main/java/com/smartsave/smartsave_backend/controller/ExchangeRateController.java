package com.smartsave.smartsave_backend.controller;

import com.smartsave.smartsave_backend.dto.ExchangeRateResponse;
import com.smartsave.smartsave_backend.dto.RateRefreshResponse;
import com.smartsave.smartsave_backend.service.ExchangeRateService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/external/rates")
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;

    public ExchangeRateController(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    @GetMapping
    public List<ExchangeRateResponse> getRates() {
        return exchangeRateService.getLatestRates();
    }

    @PostMapping("/refresh")
    public RateRefreshResponse refreshRates() {
        return exchangeRateService.refreshRates();
    }
}
