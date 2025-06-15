package com.example.bank.enums;

/**
 * 交易状态
 */
public enum TransactionStatus {
    /**
     * 处理中
     */
    PENDING,

    /**
     * 成功
     */
    SUCCESS,

    /**
     * 失败
     */
    FAILED,

    /**
     * 已取消
     */
    CANCELLED
}
