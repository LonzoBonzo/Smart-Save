package com.smartsave.smartsave_backend.repository;

import com.smartsave.smartsave_backend.domain.TransactionRecord;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRecordRepository extends JpaRepository<TransactionRecord, Long> {
    List<TransactionRecord> findByOwnerIdOrderByTransactionDateDesc(Long ownerId);
}
