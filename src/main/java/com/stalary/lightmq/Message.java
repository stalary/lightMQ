/**
 * @(#)Message.java, 2018-06-18.
 * <p>
 * Copyright 2018 Stalary.
 */
package com.stalary.lightmq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Message
 *
 * @author lirongqian
 * @since 2018/06/18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    private String topic;

    private List<MessageGroup> messageGroup;
}