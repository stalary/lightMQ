/**
 * @(#)Factory.java, 2018-06-18.
 * <p>
 * Copyright 2018 Stalary.
 */
package com.stalary.lightmq;

import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * QueueFactory
 *
 * @author lirongqian
 * @since 2018/06/18
 */
public class QueueFactory {

    /**
     * 默认分组
     */
    public static final String DEFAULT_GROUP ="master";

    private static List<Message> messageList = new ArrayList<>();

    static {
        init();
    }

    private static void init() {
        Message message = new Message();
        message.setGroup(DEFAULT_GROUP);
        messageList.add(message);
    }

    /**
     * 获取一个queue
     * @param group
     * @param topic
     * @return
     */
    public static BlockingDeque<MessageDto> getOneQueue(String group, String topic) {
        // 未传入group时，使用默认值
        if (StringUtils.isEmpty(group)) {
            group = DEFAULT_GROUP;
        }
        List<Message> allQueue = getAllQueue();
        for (Message message : allQueue) {
            if (group.equals(message.getGroup())) {
                return message.getMessage().getOrDefault(topic, null);
            }
        }
        throw new IllegalArgumentException("不存在该topic");
    }

    public static Message getOneMessage(String group) {
        List<Message> allQueue = getAllQueue();
        for (Message message : allQueue) {
            if (group.equals(message.getGroup())) {
                return message;
            }
        }
        throw new IllegalArgumentException("不存在该group");
    }

    /**
     * 获取所有的队列，debug使用
     * @return
     */
    public static List<Message> getAllQueue() {
        return messageList;
    }
}