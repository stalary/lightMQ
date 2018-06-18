/**
 * @(#)Factory.java, 2018-06-18.
 * <p>
 * Copyright 2018 Stalary.
 */
package com.stalary.lightmq;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
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

    private static class MessageQueue {
        /**
         * 通过map来进行分组消费 "group": {"topic": MessageDto}
         */
        private static Map<String, Map<String, BlockingDeque<MessageDto>>> message = new HashMap<>();
    }

    static {
        init();
    }

    private static void init() {
        Map<String, BlockingDeque<MessageDto>> defaultMap = new HashMap<>();
//        defaultMap.put(null, new LinkedBlockingDeque<>(100));
        getAllQueue().put(DEFAULT_GROUP, defaultMap);
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
        return MessageQueue.message.getOrDefault(group, new HashMap<>(1)).getOrDefault(topic, null);
    }

    public static Map<String, Map<String, BlockingDeque<MessageDto>>> getAllQueue() {
        return MessageQueue.message;
    }

    public static void registerGroup(String group) {
        Map<String, BlockingDeque<MessageDto>> defaultMap = getAllQueue().get(DEFAULT_GROUP);
        getAllQueue().put(group, defaultMap);
    }
}