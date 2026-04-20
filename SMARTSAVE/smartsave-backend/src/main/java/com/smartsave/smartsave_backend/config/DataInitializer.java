package com.smartsave.smartsave_backend.config;

import com.smartsave.smartsave_backend.domain.AppUser;
import com.smartsave.smartsave_backend.domain.SavingsGoal;
import com.smartsave.smartsave_backend.domain.TransactionRecord;
import com.smartsave.smartsave_backend.domain.TransactionTag;
import com.smartsave.smartsave_backend.domain.TransactionType;
import com.smartsave.smartsave_backend.repository.AppUserRepository;
import com.smartsave.smartsave_backend.repository.SavingsGoalRepository;
import com.smartsave.smartsave_backend.repository.TransactionRecordRepository;
import com.smartsave.smartsave_backend.repository.TransactionTagRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner seedData(
        AppUserRepository userRepository,
        SavingsGoalRepository savingsGoalRepository,
        TransactionRecordRepository transactionRepository,
        TransactionTagRepository transactionTagRepository,
        PasswordEncoder passwordEncoder
    ) {
        return args -> {
            if (userRepository.existsByEmailIgnoreCase("demo@smartsave.app")) {
                return;
            }

            AppUser demoUser = new AppUser();
            demoUser.setFullName("Demo Saver");
            demoUser.setEmail("demo@smartsave.app");
            demoUser.setPasswordHash(passwordEncoder.encode("Password123"));
            demoUser = userRepository.save(demoUser);

            TransactionTag savingsTag = transactionTagRepository.save(new TransactionTag("Savings"));
            TransactionTag groceriesTag = transactionTagRepository.save(new TransactionTag("Groceries"));
            TransactionTag paycheckTag = transactionTagRepository.save(new TransactionTag("Paycheck"));

            SavingsGoal emergencyFund = new SavingsGoal();
            emergencyFund.setName("Emergency Fund");
            emergencyFund.setTargetAmount(new BigDecimal("1500.00"));
            emergencyFund.setCurrentAmount(new BigDecimal("650.00"));
            emergencyFund.setTargetDate(LocalDate.now().plusMonths(4));
            emergencyFund.setOwner(demoUser);
            savingsGoalRepository.save(emergencyFund);

            SavingsGoal booksFund = new SavingsGoal();
            booksFund.setName("Semester Books");
            booksFund.setTargetAmount(new BigDecimal("400.00"));
            booksFund.setCurrentAmount(new BigDecimal("220.00"));
            booksFund.setTargetDate(LocalDate.now().plusMonths(2));
            booksFund.setOwner(demoUser);
            savingsGoalRepository.save(booksFund);

            TransactionRecord income = new TransactionRecord();
            income.setType(TransactionType.INCOME);
            income.setAmount(new BigDecimal("850.00"));
            income.setDescription("Campus job paycheck");
            income.setTransactionDate(LocalDate.now().minusDays(3));
            income.setOwner(demoUser);
            income.setTags(Set.of(paycheckTag, savingsTag));
            transactionRepository.save(income);

            TransactionRecord expense = new TransactionRecord();
            expense.setType(TransactionType.EXPENSE);
            expense.setAmount(new BigDecimal("62.45"));
            expense.setDescription("Weekly groceries");
            expense.setTransactionDate(LocalDate.now().minusDays(1));
            expense.setOwner(demoUser);
            expense.setTags(Set.of(groceriesTag));
            transactionRepository.save(expense);
        };
    }
}
