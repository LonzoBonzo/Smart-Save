package com.smartsave.smartsave_backend.service;

import com.smartsave.smartsave_backend.domain.AppUser;
import com.smartsave.smartsave_backend.domain.TransactionRecord;
import com.smartsave.smartsave_backend.domain.TransactionTag;
import com.smartsave.smartsave_backend.dto.TransactionRequest;
import com.smartsave.smartsave_backend.dto.TransactionResponse;
import com.smartsave.smartsave_backend.repository.TransactionRecordRepository;
import com.smartsave.smartsave_backend.repository.TransactionTagRepository;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private final TransactionRecordRepository transactionRepository;
    private final TransactionTagRepository transactionTagRepository;
    private final UserContextService userContextService;

    public TransactionService(
        TransactionRecordRepository transactionRepository,
        TransactionTagRepository transactionTagRepository,
        UserContextService userContextService
    ) {
        this.transactionRepository = transactionRepository;
        this.transactionTagRepository = transactionTagRepository;
        this.userContextService = userContextService;
    }

    public List<TransactionResponse> getCurrentUserTransactions() {
        AppUser currentUser = userContextService.requireCurrentUser();
        return transactionRepository.findByOwnerIdOrderByTransactionDateDesc(currentUser.getId())
            .stream()
            .map(this::toResponse)
            .toList();
    }

    public TransactionResponse createTransaction(TransactionRequest request) {
        AppUser currentUser = userContextService.requireCurrentUser();

        TransactionRecord transaction = new TransactionRecord();
        transaction.setType(request.type());
        transaction.setAmount(request.amount());
        transaction.setDescription(request.description());
        transaction.setTransactionDate(request.transactionDate());
        transaction.setOwner(currentUser);
        transaction.setTags(resolveTags(request.tags()));

        return toResponse(transactionRepository.save(transaction));
    }

    public List<String> getAvailableTags() {
        return transactionTagRepository.findAll()
            .stream()
            .map(TransactionTag::getName)
            .sorted(String::compareToIgnoreCase)
            .toList();
    }

    private Set<TransactionTag> resolveTags(List<String> tags) {
        Set<TransactionTag> resolved = new LinkedHashSet<>();
        for (String tagName : tags) {
            String normalized = tagName.trim();
            if (normalized.isEmpty()) {
                continue;
            }
            TransactionTag tag = transactionTagRepository.findByNameIgnoreCase(normalized)
                .orElseGet(() -> transactionTagRepository.save(new TransactionTag(normalized)));
            resolved.add(tag);
        }
        return resolved;
    }

    private TransactionResponse toResponse(TransactionRecord transaction) {
        return new TransactionResponse(
            transaction.getId(),
            transaction.getType(),
            transaction.getAmount(),
            transaction.getDescription(),
            transaction.getTransactionDate(),
            transaction.getTags().stream().map(TransactionTag::getName).sorted().toList()
        );
    }
}
