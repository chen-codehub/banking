package com.example.bank.exception;

import com.example.bank.enums.ErrorCode;

import lombok.Getter;

/**
 * 交易异常类
 */
@Getter
public class TransactionException extends RuntimeException {
    private final Integer code;

    private final String message;

    public TransactionException(ErrorCode errorEnum) {
        this.code = errorEnum.getCode();
        this.message = errorEnum.getMessage();
    }
}
