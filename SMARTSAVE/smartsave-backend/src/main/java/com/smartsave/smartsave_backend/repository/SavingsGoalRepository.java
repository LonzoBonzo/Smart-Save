package com.smartsave.smartsave_backend.repository;

import com.smartsave.smartsave_backend.domain.SavingsGoal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavingsGoalRepository extends JpaRepository<SavingsGoal, Long> {
    List<SavingsGoal> findByOwnerIdOrderByTargetDateAsc(Long ownerId);
}
