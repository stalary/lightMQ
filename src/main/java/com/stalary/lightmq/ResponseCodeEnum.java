package com.stalary.lightmq;

import lombok.Getter;

/**
 * @author Stalary
 * @description 定义返回的状态码
 * @date 2018/5/31
 */
public enum ResponseCodeEnum {

    /**
     * 成功返回0
     */
    SUCCESS(0, "成功"),

    /**
     * 失败返回-1，其他错误自行定义
     */
    FAIL(-1, "失败"),

    /**
     * 异常返回-100，其他异常自行定义
     */
    EXCEPTION(-100, "异常");

    @Getter
    private Integer code;

    @Getter
    private String msg;

    ResponseCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }


}
