package com.smartsave.smartsave_backend.service;

import com.smartsave.smartsave_backend.domain.AppUser;
import com.smartsave.smartsave_backend.domain.SavingsGoal;
import com.smartsave.smartsave_backend.dto.SavingsGoalRequest;
import com.smartsave.smartsave_backend.dto.SavingsGoalResponse;
import com.smartsave.smartsave_backend.repository.SavingsGoalRepository;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SavingsGoalService {

    private final SavingsGoalRepository savingsGoalRepository;
    private final UserContextService userContextService;

    public SavingsGoalService(SavingsGoalRepository savingsGoalRepository, UserContextService userContextService) {
        this.savingsGoalRepository = savingsGoalRepository;
        this.userContextService = userContextService;
    }

    public List<SavingsGoalResponse> getCurrentUserGoals() {
        AppUser currentUser = userContextService.requireCurrentUser();
        return savingsGoalRepository.findByOwnerIdOrderByTargetDateAsc(currentUser.getId())
            .stream()
            .map(this::toResponse)
            .toList();
    }

    public SavingsGoalResponse createGoal(SavingsGoalRequest request) {
        AppUser currentUser = userContextService.requireCurrentUser();

        SavingsGoal goal = new SavingsGoal();
        goal.setName(request.name());
        goal.setTargetAmount(request.targetAmount());
        goal.setCurrentAmount(request.currentAmount() == null ? BigDecimal.ZERO : request.currentAmount());
        goal.setTargetDate(request.targetDate());
        goal.setOwner(currentUser);

        return toResponse(savingsGoalRepository.save(goal));
    }

    private SavingsGoalResponse toResponse(SavingsGoal goal) {
        return new SavingsGoalResponse(
            goal.getId(),
            goal.getName(),
            goal.getTargetAmount(),
            goal.getCurrentAmount(),
            goal.getStatus(),
            goal.getTargetDate()
        );
    }
}
