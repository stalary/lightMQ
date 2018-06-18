/**
 * @(#)JsonResponse.java, 2018-05-31.
 * <p>
 * Copyright 2018 Stalary.
 */
package com.stalary.lightmq;

import lombok.extern.slf4j.Slf4j;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * JsonResponse
 * Json格式的返回数据
 * {
 * "success": true, // 是否为正常返回，前端可读取该方法来判断是否返回正常
 * "code": 0, // 返回的状态码，默认为0，即正常
 * "msg": "", // 返回的信息，可以返回错误信息等
 * "data": { // 返回的数据
 * <p>
 * },
 * "exception": [] //错误堆栈，无错误时不返回
 * }
 *
 * @author lirongqian
 * @since 2018/05/31
 */
@Slf4j
public class JsonResponse extends LinkedHashMap<String, Object> {

    /**
     * 默认的序列化编码
     */
    private static final long serialVersionUID = 1L;

    /**
     * 返回成功
     *
     * @return JsonResponse
     */
    public static JsonResponse success() {
        return success(null, null);
    }

    public static JsonResponse success(Object data) {
        return success(data, null);
    }

    public static JsonResponse success(Object data, String msg) {
        JsonResponse response = new JsonResponse();
        response.put("success", true);
        response.put("code", ResponseCodeEnum.SUCCESS.getCode());
        response.put("msg", msg);
        response.put("data", data);
        return response;
    }

    /**
     * 返回错误
     *
     * @return JsonResponse
     */
    public static JsonResponse fail() {
        return fail(null);
    }

    public static JsonResponse fail(String msg) {
        return fail(ResponseCodeEnum.FAIL.getCode(), msg);
    }

    public static JsonResponse fail(Integer errorCode, String msg) {
        JsonResponse response = new JsonResponse();
        response.put("success", false);
        response.put("code", errorCode);
        response.put("msg", msg);
        response.put("data", null);
        return response;
    }

    /**
     * 返回异常
     *
     * @return JsonResponse
     */
    public static JsonResponse exception(Exception e) {
        return exception(ResponseCodeEnum.EXCEPTION.getCode(), e);
    }

    public static JsonResponse exception(Integer exceptionCode, Exception e) {
        JsonResponse response = new JsonResponse();
        response.put("success", false);
        response.put("code", exceptionCode);
        response.put("msg", null);
        response.put("data", null);
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        response.put("exception", sw.toString());
        return response;
    }

    /**
     * 获取data中的数据
     *
     * @return Object
     */
    public Object getData() {
        return this.get("data");
    }

    /**
     * 设置data中的数据
     *
     * @return JsonResponse
     */
    public JsonResponse dataPut(String key, Object value) {
        mapData().put(key, value);
        return this;
    }

    public JsonResponse dataPutAll(Map<?, ?> maps) {
        mapData().putAll(maps);
        return this;
    }

    @SuppressWarnings("unchecked")
    private Map<Object, Object> mapData() {
        // 首先获取data
        Object raw = this.get("data");
        Map<Object, Object> data;
        if (raw instanceof Map) {
            return (Map<Object, Object>) raw;
        }
        if (raw == null) {
            // data为null时，则新建并写入到JsonResponse中
            data = new HashMap<>();
            this.put("data", data);
            return data;
        }
        throw new IllegalArgumentException("data [" + raw
                + "] is not instanceof map, can not put");
    }

}