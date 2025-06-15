package com.example.bank.enums;

import lombok.Getter;

/**
 * 错误码
 */
@Getter
public enum ErrorCode {
    /**
     * 创建重复交易
     */
    DUPLICATE_TRANSACTION(1001, "cannot create duplicate transaction"),

    /**
     * 操作不存在的交易
     */
    TRANSACTION_NOT_EXISTS(1002, "transaction not exists");

    private final int code;

    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
