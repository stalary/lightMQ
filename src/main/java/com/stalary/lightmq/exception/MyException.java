
package com.stalary.lightmq.exception;


import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author lirongqian
 * @description
 * @date 2018/06/18
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MyException extends RuntimeException {

    private Integer code;

    private String message;

    public MyException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public MyException(ExceptionEnum exceptionEnum) {
        super(exceptionEnum.msg);
        this.code = exceptionEnum.code;
        this.message = exceptionEnum.msg;
    }

}

