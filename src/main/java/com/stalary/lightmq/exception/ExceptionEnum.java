package com.stalary.lightmq.exception;

/**
 * @author Stalary
 * @description
 * @date 2018/4/28
 */
public enum ExceptionEnum {

    NO_TOPIC(1001, "不存在该topic");

    Integer code;

    String msg;

    ExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
