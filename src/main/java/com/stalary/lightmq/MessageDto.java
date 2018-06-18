/**
 * @(#)MessageDto.java, 2018-06-18.
 * <p>
 * Copyright 2018 Stalary.
 */
package com.stalary.lightmq;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * MessageDto
 *
 * @author lirongqian
 * @since 2018/06/18
 */
@Data
@AllArgsConstructor
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

}