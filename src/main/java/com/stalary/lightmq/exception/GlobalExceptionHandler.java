
package com.stalary.lightmq.exception;

import com.stalary.lightmq.JsonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * GlobalExceptionHandler
 *
 * @author lirongqian
 * @since 2018/06/18
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public JsonResponse handle(Exception e) {
        if (e instanceof MyException) {
            MyException myException = (MyException) e;
            return JsonResponse.fail(myException.getCode(), myException.getMessage());
        } else {
            log.error("[系统异常] ", e);
            return JsonResponse.exception(e);
        }
    }

}