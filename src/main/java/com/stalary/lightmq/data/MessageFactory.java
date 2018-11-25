/**
 * @(#)MessageFactory.java, 2018-11-20.
 *
 * Copyright 2018 Stalary.
 */
package com.stalary.lightmq.data;

import com.google.common.collect.Lists;
import com.stalary.lightmq.exception.ExceptionEnum;
import com.stalary.lightmq.exception.MyException;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * MessageFactory
 *
 * @author lirongqian
 * @since 2018/11/20
 */
public class MessageFactory {

    /** topic:group的映射 **/
    public static Map<String, List<MessageGroup>> groupMap = new ConcurrentHashMap<>();

    /** topic:(offset:message)的映射 **/
    public static Map<String, Map<Long, MessageDto>> messageMap = new ConcurrentHashMap<>();

    public static void addGroup(String topic, String group) {
        if (StringUtils.isEmpty(topic)) {
            throw new MyException(ExceptionEnum.NULL_VALUE);
        }
        if (!messageMap.containsKey(topic)) {
            throw new MyException(ExceptionEnum.NO_TOPIC);
        }
        if (groupMap.containsKey(topic)) {
            List<MessageGroup> messageGroups = groupMap.get(topic);
            messageGroups.forEach(message -> {
                if (group.equals(message.getGroup())) {
                    throw new MyException(ExceptionEnum.REPEAT_GROUP);
                }
            });
            messageGroups.add(new MessageGroup());
        } else {
            groupMap.put(topic, Lists.newArrayList(new MessageGroup()));
        }
    }

    public static void addTopic(String topic) {
        if (messageMap.containsKey(topic)) {
            throw new MyException(ExceptionEnum.REPEAT_TOPIC);
        }
        messageMap.put(topic, new HashMap<>());
        addGroup(topic, Constant.DEFAULT_GROUP);
    }
}