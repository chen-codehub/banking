package com.example.bank.entity;

import com.example.bank.enums.TransactionStatus;
import com.example.bank.enums.TransactionType;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 交易实体类
 */
@Data
@Builder
public class Transaction {
    /**
     * 交易id
     */
    private Long transactionId;

    /**
     * 付款方id
     */
    private String senderId;

    /**
     * 收款方id
     */
    private String recipientId;

    /**
     * 交易金额
     */
    private BigDecimal amount;

    /**
     * 货币类型
     */
    private String currency;

    /**
     * 交易类型
     */
    private TransactionType type;

    /**
     * 交易状态
     */
    private TransactionStatus status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
