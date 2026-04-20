package com.smartsave.smartsave_backend.repository;

import com.smartsave.smartsave_backend.domain.ExchangeRateSnapshot;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeRateSnapshotRepository extends JpaRepository<ExchangeRateSnapshot, Long> {
    List<ExchangeRateSnapshot> findTop10ByOrderByFetchedAtDesc();
}
