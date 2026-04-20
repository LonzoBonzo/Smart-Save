package com.smartsave.smartsave_backend.controller;

import com.smartsave.smartsave_backend.dto.TransactionRequest;
import com.smartsave.smartsave_backend.dto.TransactionResponse;
import com.smartsave.smartsave_backend.service.TransactionService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public List<TransactionResponse> getTransactions() {
        return transactionService.getCurrentUserTransactions();
    }

    @GetMapping("/tags")
    public List<String> getTags() {
        return transactionService.getAvailableTags();
    }

    @PostMapping
    public TransactionResponse createTransaction(@Valid @RequestBody TransactionRequest request) {
        return transactionService.createTransaction(request);
    }
}
