package com.example.bank.entity;

import com.example.bank.enums.TransactionStatus;
import com.example.bank.enums.TransactionType;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 交易请求体
 */
@Data
public class TransactionRequest {
    /**
     * 创建交易标识
     */
    public class Create {
    }

    /**
     * 修改交易标识
     */
    public class Update {
    }

    /**
     * 请求id
     */
    @NotNull(groups = Create.class)
    private String requestId;

    /**
     * 交易id
     */
    @NotNull(groups = Update.class)
    private Long transactionId;

    /**
     * 付款方id
     */
    @NotNull
    private String senderId;

    /**
     * 收款方id
     */
    @NotNull
    private String recipientId;

    /**
     * 交易金额
     */
    @Positive
    private BigDecimal amount;

    /**
     * 货币类型
     */
    @NotNull
    private String currency;

    /**
     * 交易类型
     */
    @NotNull
    private TransactionType type;

    /**
     * 交易状态
     */
    @NotNull
    private TransactionStatus status;
}
