/**
 * @(#)MessageDto.java, 2018-06-18.
 * <p>
 * Copyright 2018 Stalary.
 */
package com.stalary.lightmq.data;

import lombok.Data;

/**
 * MessageDto
 * 存储消息
 * @author lirongqian
 * @since 2018/06/18
 */
@Data
public class MessageDto {

    /**
     * 主题
     */
    private String topic;

    /**
     * 键，可以为空
     */
    private String key;

    /**
     * 值
     */
    private String value;

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

    public MessageDto() {
    }
}