package com.smartsave.smartsave_backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartsave.smartsave_backend.domain.TransactionType;
import com.smartsave.smartsave_backend.dto.TransactionRequest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "demo@smartsave.app")
    void shouldCreateTransaction() throws Exception {

        TransactionRequest request = new TransactionRequest(
                TransactionType.EXPENSE,
                new BigDecimal("25.50"),
                "Lunch",
                LocalDate.of(2026, 4, 30),
                List.of("Food")
        );

        mockMvc.perform(post("/api/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }
}
