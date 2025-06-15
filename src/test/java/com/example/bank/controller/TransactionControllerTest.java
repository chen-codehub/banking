package com.example.bank.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.bank.entity.Transaction;
import com.example.bank.entity.TransactionRequest;
import com.example.bank.enums.TransactionStatus;
import com.example.bank.enums.TransactionType;
import com.example.bank.service.TransactionService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.List;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    /**
     * Tests successful creation of a transaction with valid data.
     */
    @Test
    void testCreateTransaction_Success() throws Exception {
        TransactionRequest request = new TransactionRequest();
        request.setRequestId("req123");
        request.setSenderId("sender123");
        request.setRecipientId("recipient123");
        request.setAmount(new BigDecimal("100.00"));
        request.setCurrency("CNY");
        request.setType(TransactionType.TRANSFER);
        request.setStatus(TransactionStatus.PENDING);

        Transaction mockTransaction = Transaction.builder().transactionId(1L).senderId("sender123").recipientId(
            "recipient123").amount(new BigDecimal("100.00")).currency("CNY").type(TransactionType.TRANSFER).status(
                TransactionStatus.PENDING).build();

        when(transactionService.createTransaction(any(TransactionRequest.class))).thenReturn(mockTransaction);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/bank/transactions").contentType(MediaType.APPLICATION_JSON)
            .content("""
                {
                    "requestId": "req123",
                    "senderId": "sender123",
                    "recipientId": "recipient123",
                    "amount": 100.00,
                    "currency": "CNY",
                    "type": "TRANSFER",
                    "status": "PENDING"
                }
                """)).andExpect(status().isOk()).andExpect(jsonPath("$.transactionId").value(1L)).andExpect(jsonPath(
                "$.senderId").value("sender123")).andExpect(jsonPath("$.recipientId").value("recipient123")).andExpect(
                    jsonPath("$.amount").value(100.00)).andExpect(jsonPath("$.currency").value("CNY")).andExpect(
                        jsonPath("$.type").value("TRANSFER")).andExpect(jsonPath("$.status").value("PENDING"));
    }

    /**
     * Tests that creating a transaction with missing required fields results in validation error.
     */
    @Test
    void testCreateTransaction_InvalidRequest_MissingFields() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/bank/transactions").contentType(MediaType.APPLICATION_JSON)
            .content("""
                {
                    "requestId": "req123",
                    "senderId": "sender123",
                    "recipientId": "recipient123",
                    "amount": 100.00
                }
                """)).andExpect(status().isInternalServerError());
    }

    /**
     * Tests that creating a transaction with invalid numeric values (e.g., negative amount) fails validation.
     */
    @Test
    void testCreateTransaction_InvalidRequest_NegativeAmount() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/bank/transactions").contentType(MediaType.APPLICATION_JSON)
            .content("""
                {
                    "requestId": "req123",
                    "senderId": "sender123",
                    "recipientId": "recipient123",
                    "amount": -100.00,
                    "currency": "CNY",
                    "type": "TRANSFER",
                    "status": "PENDING"
                }
                """)).andExpect(status().isInternalServerError());
    }

    /**
     * Tests that creating a transaction with invalid enum values fails validation.
     */
    @Test
    void testCreateTransaction_InvalidRequest_InvalidEnumValue() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/bank/transactions").contentType(MediaType.APPLICATION_JSON)
            .content("""
                {
                    "requestId": "req123",
                    "senderId": "sender123",
                    "recipientId": "recipient123",
                    "amount": 100.00,
                    "currency": "INVALID_CURRENCY",
                    "type": "INVALID_TYPE",
                    "status": "INVALID_STATUS"
                }
                """)).andExpect(status().isInternalServerError());
    }

    /**
     * Tests that creating a transaction with null body fails validation.
     */
    @Test
    void testCreateTransaction_NullRequestBody() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/bank/transactions").contentType(MediaType.APPLICATION_JSON)
            .content("")).andExpect(status().isInternalServerError());
    }

    /**
     * Tests successful update of a transaction with valid data.
     */
    @Test
    void testUpdateTransaction_Success() throws Exception {
        TransactionRequest request = new TransactionRequest();
        request.setTransactionId(1L);
        request.setSenderId("sender123");
        request.setRecipientId("recipient123");
        request.setAmount(new BigDecimal("100.00"));
        request.setCurrency("CNY");
        request.setType(TransactionType.TRANSFER);
        request.setStatus(TransactionStatus.PENDING);

        Transaction mockTransaction = Transaction.builder().transactionId(1L).senderId("sender123").recipientId(
            "recipient123").amount(new BigDecimal("100.00")).currency("CNY").type(TransactionType.TRANSFER).status(
                TransactionStatus.PENDING).build();

        when(transactionService.updateTransaction(any(TransactionRequest.class))).thenReturn(mockTransaction);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/bank/transactions").contentType(MediaType.APPLICATION_JSON)
            .content("""
                {
                    "transactionId": 1,
                    "senderId": "sender123",
                    "recipientId": "recipient123",
                    "amount": 100.00,
                    "currency": "CNY",
                    "type": "TRANSFER",
                    "status": "PENDING"
                }
                """)).andExpect(status().isOk()).andExpect(jsonPath("$.transactionId").value(1L)).andExpect(jsonPath(
                "$.senderId").value("sender123")).andExpect(jsonPath("$.recipientId").value("recipient123")).andExpect(
                    jsonPath("$.amount").value(100.00)).andExpect(jsonPath("$.currency").value("CNY")).andExpect(
                        jsonPath("$.type").value("TRANSFER")).andExpect(jsonPath("$.status").value("PENDING"));
    }

    /**
     * Tests that updating a transaction with missing required fields results in validation error.
     */
    @Test
    void testUpdateTransaction_InvalidRequest_MissingFields() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/bank/transactions").contentType(MediaType.APPLICATION_JSON)
            .content("""
                {
                    "transactionId": 1,
                    "senderId": "sender123",
                    "recipientId": "recipient123",
                    "amount": 100.00
                }
                """)).andExpect(status().isInternalServerError());
    }

    /**
     * Tests that updating a transaction with invalid numeric values (e.g., negative amount) fails validation.
     */
    @Test
    void testUpdateTransaction_InvalidRequest_NegativeAmount() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/bank/transactions").contentType(MediaType.APPLICATION_JSON)
            .content("""
                {
                    "transactionId": 1,
                    "senderId": "sender123",
                    "recipientId": "recipient123",
                    "amount": -100.00,
                    "currency": "CNY",
                    "type": "TRANSFER",
                    "status": "PENDING"
                }
                """)).andExpect(status().isInternalServerError());
    }

    /**
     * Tests that updating a transaction with invalid enum values fails validation.
     */
    @Test
    void testUpdateTransaction_InvalidRequest_InvalidEnumValue() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/bank/transactions").contentType(MediaType.APPLICATION_JSON)
            .content("""
                {
                    "transactionId": 1,
                    "senderId": "sender123",
                    "recipientId": "recipient123",
                    "amount": 100.00,
                    "currency": "INVALID_CURRENCY",
                    "type": "INVALID_TYPE",
                    "status": "INVALID_STATUS"
                }
                """)).andExpect(status().isInternalServerError());
    }

    /**
     * Tests that updating a transaction with null body fails validation.
     */
    @Test
    void testUpdateTransaction_NullRequestBody() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/bank/transactions").contentType(MediaType.APPLICATION_JSON)
            .content("")).andExpect(status().isInternalServerError());
    }

    /**
     * Tests successful deletion of a transaction with a valid transaction ID.
     */
    @Test
    void testDeleteTransaction_Success() throws Exception {
        Long transactionId = 1L;
        when(transactionService.deleteTransaction(transactionId)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/bank/transactions").param("transactionId", transactionId
            .toString())).andExpect(status().isOk()).andExpect(jsonPath("$").value(true));
    }

    /**
     * Tests behavior when an invalid transaction ID (e.g., negative value) is provided.
     */
    @Test
    void testDeleteTransaction_InvalidId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/bank/transactions").param("transactionId", "-1"))
            .andExpect(status().isInternalServerError());
    }

    /**
     * Tests behavior when an exception occurs during transaction deletion.
     */
    @Test
    void testDeleteTransaction_Exception() throws Exception {
        Long transactionId = 1L;
        when(transactionService.deleteTransaction(transactionId)).thenThrow(new RuntimeException("Deletion failed"));

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/bank/transactions").param("transactionId", transactionId
            .toString())).andExpect(status().isInternalServerError());
    }

    /**
     * Tests successful retrieval of transactions with valid pagination parameters.
     */
    @Test
    void testGetTransactions_Success() throws Exception {
        Transaction mockTransaction = Transaction.builder().transactionId(1L).senderId("sender123").recipientId(
            "recipient123").amount(new BigDecimal("100.00")).currency("CNY").type(TransactionType.TRANSFER).status(
                TransactionStatus.PENDING).build();

        when(transactionService.getTransactions(1, 10)).thenReturn(List.of(mockTransaction));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/bank/transactions").param("pageNumber", "1").param(
            "pageSize", "10")).andExpect(status().isOk()).andExpect(jsonPath("$[0].transactionId").value(1L)).andExpect(
                jsonPath("$[0].senderId").value("sender123")).andExpect(jsonPath("$[0].recipientId").value(
                    "recipient123")).andExpect(jsonPath("$[0].amount").value(100.00)).andExpect(jsonPath(
                        "$[0].currency").value("CNY")).andExpect(jsonPath("$[0].type").value("TRANSFER")).andExpect(
                            jsonPath("$[0].status").value("PENDING"));
    }

    /**
     * Tests behavior when an invalid (non-positive) page number is provided.
     */
    @Test
    void testGetTransactions_InvalidPageNumber() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/bank/transactions").param("pageNumber", "-1").param(
            "pageSize", "10")).andExpect(status().isInternalServerError());
    }

    /**
     * Tests behavior when an invalid (non-positive) page size is provided.
     */
    @Test
    void testGetTransactions_InvalidPageSize() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/bank/transactions").param("pageNumber", "1").param(
            "pageSize", "-10")).andExpect(status().isInternalServerError());
    }

    /**
     * Tests behavior when an exception occurs during transaction retrieval.
     */
    @Test
    void testGetTransactions_Exception() throws Exception {
        when(transactionService.getTransactions(1, 10)).thenThrow(new RuntimeException("Retrieval failed"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/bank/transactions").param("pageNumber", "1").param(
            "pageSize", "10")).andExpect(status().isInternalServerError());
    }
}