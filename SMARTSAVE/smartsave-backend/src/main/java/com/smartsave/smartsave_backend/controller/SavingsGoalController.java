package com.smartsave.smartsave_backend.controller;

import com.smartsave.smartsave_backend.dto.SavingsGoalRequest;
import com.smartsave.smartsave_backend.dto.SavingsGoalResponse;
import com.smartsave.smartsave_backend.service.SavingsGoalService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/goals")
public class SavingsGoalController {

    private final SavingsGoalService savingsGoalService;

    public SavingsGoalController(SavingsGoalService savingsGoalService) {
        this.savingsGoalService = savingsGoalService;
    }

    @GetMapping
    public List<SavingsGoalResponse> getGoals() {
        return savingsGoalService.getCurrentUserGoals();
    }

    @PostMapping
    public SavingsGoalResponse createGoal(@Valid @RequestBody SavingsGoalRequest request) {
        return savingsGoalService.createGoal(request);
    }
}
