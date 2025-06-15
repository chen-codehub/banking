package com.example.bank.service;

import com.example.bank.enums.ErrorCode;
import com.example.bank.entity.Transaction;
import com.example.bank.exception.TransactionException;
import com.example.bank.entity.TransactionRequest;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * 交易服务实现类
 */
@Service
public class TransactionService {
    private final ConcurrentSkipListMap<Long, Transaction> transactionsMap = new ConcurrentSkipListMap<>();

    private final ConcurrentSkipListSet<String> requestIdSet = new ConcurrentSkipListSet<>();

    private final AtomicLong idGenerator = new AtomicLong(1);

    /**
     * 创建交易
     *
     * @param request 创建交易请求体
     * @return 创建结果
     */
    public Transaction createTransaction(TransactionRequest request) {
        if (requestIdSet.contains(request.getRequestId())) {
            throw new TransactionException(ErrorCode.DUPLICATE_TRANSACTION);
        }
        Transaction transaction = Transaction.builder()
            .transactionId(idGenerator.getAndIncrement())
            .senderId(request.getSenderId())
            .recipientId(request.getRecipientId())
            .amount(request.getAmount())
            .currency(request.getCurrency())
            .type(request.getType())
            .status(request.getStatus())
            .createTime(new Date())
            .build();
        transactionsMap.put(transaction.getTransactionId(), transaction);
        requestIdSet.add(request.getRequestId());
        return transaction;
    }

    /**
     * 修改交易
     *
     * @param request 修改交易请求体
     * @return 修改结果
     */
    public Transaction updateTransaction(TransactionRequest request) {
        if (!transactionsMap.containsKey(request.getTransactionId())) {
            throw new TransactionException(ErrorCode.TRANSACTION_NOT_EXISTS);
        }
        Transaction transaction = Transaction.builder()
            .transactionId(request.getTransactionId())
            .senderId(request.getSenderId())
            .recipientId(request.getRecipientId())
            .amount(request.getAmount())
            .currency(request.getCurrency())
            .type(request.getType())
            .status(request.getStatus())
            .updateTime(new Date())
            .build();
        transactionsMap.put(transaction.getTransactionId(), transaction);
        return transaction;
    }

    /**
     * 删除交易
     *
     * @param transactionId 交易id
     * @return 删除结果
     */
    public boolean deleteTransaction(Long transactionId) {
        if (!transactionsMap.containsKey(transactionId)) {
            throw new TransactionException(ErrorCode.TRANSACTION_NOT_EXISTS);
        }
        transactionsMap.remove(transactionId);
        return true;
    }

    /**
     * 分页查询交易列表
     *
     * @param pageNumber 页码
     * @param pageSize 每页条数
     * @return 查询结果
     */
    public List<Transaction> getTransactions(int pageNumber, int pageSize) {
        int skipOffset = (pageNumber - 1) * pageSize;
        return transactionsMap.values().stream().skip(skipOffset).limit(pageSize).collect(Collectors.toList());
    }
}
