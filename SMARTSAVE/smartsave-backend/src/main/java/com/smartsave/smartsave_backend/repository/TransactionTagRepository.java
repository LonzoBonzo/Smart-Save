package com.smartsave.smartsave_backend.repository;

import com.smartsave.smartsave_backend.domain.TransactionTag;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionTagRepository extends JpaRepository<TransactionTag, Long> {
    Optional<TransactionTag> findByNameIgnoreCase(String name);
}
