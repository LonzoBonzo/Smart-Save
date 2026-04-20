package com.smartsave.smartsave_backend.service;

import com.smartsave.smartsave_backend.domain.AppUser;
import com.smartsave.smartsave_backend.domain.TransactionType;
import com.smartsave.smartsave_backend.dto.DashboardResponse;
import com.smartsave.smartsave_backend.repository.SavingsGoalRepository;
import com.smartsave.smartsave_backend.repository.TransactionRecordRepository;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    private final UserContextService userContextService;
    private final SavingsGoalRepository savingsGoalRepository;
    private final TransactionRecordRepository transactionRepository;

    public DashboardService(
        UserContextService userContextService,
        SavingsGoalRepository savingsGoalRepository,
        TransactionRecordRepository transactionRepository
    ) {
        this.userContextService = userContextService;
        this.savingsGoalRepository = savingsGoalRepository;
        this.transactionRepository = transactionRepository;
    }

    public DashboardResponse getDashboard() {
        AppUser user = userContextService.requireCurrentUser();
        var goals = savingsGoalRepository.findByOwnerIdOrderByTargetDateAsc(user.getId());
        var transactions = transactionRepository.findByOwnerIdOrderByTransactionDateDesc(user.getId());

        BigDecimal totalSaved = goals.stream()
            .map(goal -> goal.getCurrentAmount() == null ? BigDecimal.ZERO : goal.getCurrentAmount())
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalIncome = transactions.stream()
            .filter(tx -> tx.getType() == TransactionType.INCOME)
            .map(tx -> tx.getAmount() == null ? BigDecimal.ZERO : tx.getAmount())
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalExpenses = transactions.stream()
            .filter(tx -> tx.getType() == TransactionType.EXPENSE)
            .map(tx -> tx.getAmount() == null ? BigDecimal.ZERO : tx.getAmount())
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new DashboardResponse(
            user.getFullName(),
            goals.size(),
            transactions.size(),
            totalSaved,
            totalIncome,
            totalExpenses
        );
    }
}
