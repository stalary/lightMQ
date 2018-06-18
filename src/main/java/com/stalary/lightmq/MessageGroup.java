/**
 * @(#)MessageGroup.java, 2018-06-18.
 * <p>
 * Copyright 2018 Stalary.
 */
package com.stalary.lightmq;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.concurrent.LinkedBlockingDeque;

/**
 * MessageGroup
 *
 * @author lirongqian
 * @since 2018/06/18
 */
@Data
@AllArgsConstructor
public class MessageGroup {

    private String group;

    private LinkedBlockingDeque<MessageDto> message;
}