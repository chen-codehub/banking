package com.example.bank.controller;

import com.example.bank.entity.Transaction;
import com.example.bank.entity.TransactionRequest;
import com.example.bank.entity.TransactionRequest.Create;
import com.example.bank.entity.TransactionRequest.Update;
import com.example.bank.service.TransactionService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 交易系统控制器类
 */
@RestController
@RequestMapping("/api/v1/bank/transactions")
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    /**
     * 创建交易
     *
     * @param request 创建交易请求体
     * @return 创建结果
     */
    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody @Valid @Validated({Create.class})
        TransactionRequest request) {
        Transaction transaction = transactionService.createTransaction(request);
        return ResponseEntity.ok().body(transaction);
    }

    /**
     * 修改交易
     *
     * @param request 修改交易请求体
     * @return 修改结果
     */
    @PutMapping
    public ResponseEntity<Transaction> updateTransaction(@RequestBody @Valid @Validated({Update.class})
        TransactionRequest request) {
        Transaction transaction = transactionService.updateTransaction(request);
        return ResponseEntity.ok().body(transaction);
    }

    /**
     * 删除交易
     *
     * @param transactionId 交易id
     * @return 删除结果
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteTransaction(@Positive Long transactionId) {
        boolean isSuccess = transactionService.deleteTransaction(transactionId);
        return ResponseEntity.ok().body(isSuccess);
    }

    /**
     * 分页查询交易列表
     *
     * @param pageNumber 页码
     * @param pageSize 每页条数
     * @return 查询结果
     */
    @GetMapping
    public ResponseEntity<List<Transaction>> getTransactions(@Positive int pageNumber, @Positive int pageSize) {
        List<Transaction> transactions = transactionService.getTransactions(pageNumber, pageSize);
        return ResponseEntity.ok().body(transactions);
    }
}
