/**
 * @(#)Factory.java, 2018-06-18.
 * <p>
 * Copyright 2018 Stalary.
 */
package com.stalary.lightmq;

import com.stalary.lightmq.data.Message;
import com.stalary.lightmq.data.MessageDto;
import com.stalary.lightmq.data.MessageGroup;
import com.stalary.lightmq.exception.ExceptionEnum;
import com.stalary.lightmq.exception.MyException;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.concurrent.BlockingDeque;

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
    public static final String DEFAULT_GROUP = "master";

    private static List<Message> messageList = new ArrayList<>();

    /**
     * 获取一个queue
     *
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
            if (topic.equals(message.getTopic())) {
                List<MessageGroup> messageGroup = message.getMessageGroup();
                for (MessageGroup g : messageGroup) {
                    if (group.equals(g.getGroup())) {
                        return g.getMessage();
                    }
                }
            }
        }
        throw new MyException(ExceptionEnum.NO_TOPIC);
    }

    public static Message getOneMessage(String topic) {
        List<Message> allQueue = getAllQueue();
        for (Message message : allQueue) {
            if (topic.equals(message.getTopic())) {
                return message;
            }
        }
        throw new MyException(ExceptionEnum.NO_TOPIC);
    }

    /**
     * 获取所有的队列，debug使用
     *
     * @return
     */
    public static List<Message> getAllQueue() {
        return messageList;
    }

    public static void setAllQueue(List<Message> messages) {
        messageList = messages;
    }
}