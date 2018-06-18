/**
 * @(#)MessageDto.java, 2018-06-18.
 * <p>
 * Copyright 2018 Stalary.
 */
package com.stalary.lightmq;

import lombok.Data;

/**
 * MessageDto
 *
 * @author lirongqian
 * @since 2018/06/18
 */
@Data
public class MessageDto {

    /**
     * 主题
     */
    private final String topic;

    /**
     * 键，可以为空
     */
    private final String key;

    /**
     * 值
     */
    private final String value;

    public MessageDto(String topic, String value) {
        this.topic = topic;
        this.value = value;
        this.key = "";
    }

    public MessageDto(String topic, String key, String value) {
        this.topic = topic;
        this.key = key;
        this.value = value;
    }
}