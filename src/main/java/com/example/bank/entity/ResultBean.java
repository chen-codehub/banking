package com.example.bank.entity;

import lombok.Data;

/**
 * 返回结果实体类
 */
@Data
public class ResultBean {
    private int code;

    private String message;

    private Object data;

    public ResultBean(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 错误结果
     *
     * @return 错误结果实体
     */
    public static ResultBean error() {
        return new ResultBean(500, "system error");
    }
}
