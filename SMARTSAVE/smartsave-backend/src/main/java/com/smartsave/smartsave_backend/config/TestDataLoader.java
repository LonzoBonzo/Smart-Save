package com.smartsave.config;

import com.smartsave.model.*;
import com.smartsave.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Set;

@Component
@Profile("dev") // runs only in dev mode
@RequiredArgsConstructor
public class TestDataLoader implements CommandLineRunner {

    private final AppUserRepository userRepo;
    private final SavingsGoalRepository goalRepo;
    private final TransactionRepository transactionRepo;
    private final TransactionTagRepository tagRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        if (userRepo.count() > 0) return;

        // 👤 ADMIN USER
        AppUser admin = new AppUser();
        admin.setFullName("Admin User");
        admin.setEmail("admin@smartsave.app");
        admin.setPassword(passwordEncoder.encode("Admin123"));
        admin.setRole(Role.ADMIN);
        userRepo.save(admin);

        // 🏷 TAGS
        TransactionTag food = tagRepo.save(new TransactionTag("Food"));
        TransactionTag bills = tagRepo.save(new TransactionTag("Bills"));

        // 🎯 GOAL
        SavingsGoal goal = new SavingsGoal();
        goal.setName("Emergency Fund");
        goal.setTargetAmount(2000.0);
        goal.setCurrentAmount(500.0);
        goal.setUser(admin);
        goalRepo.save(goal);

        // 💸 TRANSACTIONS
        TransactionRecord t1 = new TransactionRecord();
        t1.setType(TransactionType.EXPENSE);
        t1.setAmount(45.00);
        t1.setDescription("Groceries");
        t1.setTransactionDate(LocalDate.now());
        t1.setUser(admin);
        t1.setTags(Set.of(food));

        TransactionRecord t2 = new TransactionRecord();
        t2.setType(TransactionType.INCOME);
        t2.setAmount(300.00);
        t2.setDescription("Part-time job");
        t2.setTransactionDate(LocalDate.now());
        t2.setUser(admin);
        t2.setTags(Set.of(bills));

        transactionRepo.saveAll(Set.of(t1, t2));
    }
}
