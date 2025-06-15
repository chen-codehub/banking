package com.example.bank.exception;

import com.example.bank.entity.ResultBean;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 */
@RestControllerAdvice
public class TransactionExceptionHandler {
    /**
     * 处理自定义的交易异常
     *
     * @param exception 交易异常
     * @return 异常结果
     */
    @ExceptionHandler(TransactionException.class)
    public ResponseEntity<ResultBean> handleTransactionException(TransactionException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResultBean(exception.getCode(), exception
            .getMessage()));
    }

    /**
     * 处理其他异常
     *
     * @param exception 其他异常
     * @return 异常结果
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResultBean> handleException(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResultBean.error());
    }
}
