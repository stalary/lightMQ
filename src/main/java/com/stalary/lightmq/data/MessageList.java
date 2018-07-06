/**
 * @(#)MessageList.java, 2018-06-18.
 * <p>
 * Copyright 2018 Stalary.
 */
package com.stalary.lightmq.data;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * MessageList
 * 所有消息
 * @author lirongqian
 * @since 2018/06/18
 */
@Data
@AllArgsConstructor
public class MessageList {

    List<Message> messageList;
}