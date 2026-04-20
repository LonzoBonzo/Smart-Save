package com.smartsave.smartsave_backend.service;

import com.smartsave.smartsave_backend.domain.ExchangeRateSnapshot;
import com.smartsave.smartsave_backend.dto.ExchangeRateResponse;
import com.smartsave.smartsave_backend.dto.RateRefreshResponse;
import com.smartsave.smartsave_backend.exception.ApiException;
import com.smartsave.smartsave_backend.repository.ExchangeRateSnapshotRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class ExchangeRateService {

    private final RestTemplate restTemplate;
    private final ExchangeRateSnapshotRepository exchangeRateSnapshotRepository;
    private final String ratesUrl;

    public ExchangeRateService(
        RestTemplateBuilder restTemplateBuilder,
        ExchangeRateSnapshotRepository exchangeRateSnapshotRepository,
        @Value("${app.external.rates-url}") String ratesUrl
    ) {
        this.restTemplate = restTemplateBuilder.build();
        this.exchangeRateSnapshotRepository = exchangeRateSnapshotRepository;
        this.ratesUrl = ratesUrl;
    }

    public List<ExchangeRateResponse> getLatestRates() {
        return exchangeRateSnapshotRepository.findTop10ByOrderByFetchedAtDesc()
            .stream()
            .sorted(Comparator.comparing(ExchangeRateSnapshot::getFetchedAt).reversed())
            .map(this::toResponse)
            .toList();
    }

    public RateRefreshResponse refreshRates() {
        try {
            FrankfurterResponse response = restTemplate.getForObject(ratesUrl, FrankfurterResponse.class);
            if (response == null || response.rates() == null || response.rates().isEmpty()) {
                throw new ApiException(HttpStatus.BAD_GATEWAY, "External rate service returned no data");
            }

            LocalDateTime fetchedAt = LocalDateTime.now();
            List<ExchangeRateResponse> savedRates = new ArrayList<>();

            for (Map.Entry<String, BigDecimal> entry : response.rates().entrySet()) {
                ExchangeRateSnapshot snapshot = new ExchangeRateSnapshot();
                snapshot.setBaseCurrency(response.base());
                snapshot.setTargetCurrency(entry.getKey());
                snapshot.setRate(entry.getValue());
                snapshot.setFetchedAt(fetchedAt);
                snapshot.setSource("frankfurter.app");
                savedRates.add(toResponse(exchangeRateSnapshotRepository.save(snapshot)));
            }

            return new RateRefreshResponse("success", "Rates refreshed successfully", savedRates);
        } catch (RestClientException ex) {
            throw new ApiException(
                HttpStatus.TOO_MANY_REQUESTS,
                "Unable to refresh rates right now. Please wait and try again later."
            );
        }
    }

    private ExchangeRateResponse toResponse(ExchangeRateSnapshot snapshot) {
        return new ExchangeRateResponse(
            snapshot.getId(),
            snapshot.getBaseCurrency(),
            snapshot.getTargetCurrency(),
            snapshot.getRate(),
            snapshot.getFetchedAt(),
            snapshot.getSource()
        );
    }

    private record FrankfurterResponse(String amount, String base, String date, Map<String, BigDecimal> rates) {
    }
}
