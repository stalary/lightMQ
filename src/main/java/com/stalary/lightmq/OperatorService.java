/**
 * @(#)OperatorService.java, 2018-06-18.
 * <p>
 * Copyright 2018 Stalary.
 */
package com.stalary.lightmq;

import com.stalary.lightmq.data.Constant;
import com.stalary.lightmq.data.MessageDto;
import com.stalary.lightmq.data.MessageFactory;
import com.stalary.lightmq.data.MessageGroup;
import com.stalary.lightmq.exception.ExceptionEnum;
import com.stalary.lightmq.exception.MyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalLong;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * OperatorService
 *
 * @author lirongqian
 * @since 2018/06/18
 */
@Service
@Slf4j
public class OperatorService {

    private ExecutorService executor = Executors.newFixedThreadPool(10);

    /**
     * 同步生产消息
     *
     * @param topic
     * @param key
     * @param value
     */
    public void produceSync(String topic, String key, String value, boolean auto) {
        Map<Long, MessageDto> message = getOneMessage(topic, auto);
        // 求出最大位移
        OptionalLong max = message.keySet().stream().mapToLong(v -> v).max();
        long offset = 0;
        if (max.isPresent()) {
            offset = max.getAsLong() + 1;
        }
        message.put(offset, new MessageDto(offset, topic, key, value));
    }

    /**
     * 异步生产消息,不保证消息的顺序
     */
    public void produceAsync(String topic, String key, String value, boolean auto) {
        // 直接放入线程池
        executor.execute(() -> produceSync(topic, key, value, auto));
    }

    /** 消费 **/
    public MessageDto consume(String group, String topic, boolean auto) {
        Map<Long, MessageDto> message = getOneMessage(topic, auto);
        MessageGroup oneGroup = getOneGroup(topic, group);
        Long offset = oneGroup.getOffset();
        MessageDto messageDto = message.get(offset);
        if (messageDto == null) {
            return null;
        }
        // 后移offset
        oneGroup.setOffset(offset + 1);
        return messageDto;
    }

    /**
     * 注册分组
     **/
    public void registerGroup(String group, String topic) {
        MessageFactory.addGroup(topic, group);
    }

    /**
     * 注册topic
     **/
    public void registerTopic(String topic) {
        MessageFactory.addTopic(topic);
        // 创建默认分组
        MessageFactory.addGroup(topic, Constant.DEFAULT_GROUP);
    }

    public Map<Long, MessageDto> getOneMessage(String topic, boolean auto) {
        Map<String, Map<Long, MessageDto>> messageMap = getAllMessage();
        Map<Long, MessageDto> message = messageMap.get(topic);
        if (message == null) {
            // 是否自动创建
            if (auto) {
                MessageFactory.addTopic(topic);
                message = messageMap.get(topic);
            } else {
                throw new MyException(ExceptionEnum.NO_TOPIC);
            }
        }
        return message;
    }

    /**
     * 获取当前所有消息
     **/
    public Map<String, Map<Long, MessageDto>> getAllMessage() {
        return MessageFactory.messageMap;
    }

    /**
     * 获取一个消费者组
     **/
    public MessageGroup getOneGroup(String topic, String group) {
        Map<String, List<MessageGroup>> groupMap = MessageFactory.groupMap;
        List<MessageGroup> messageGroups = groupMap.get(topic);
        for (MessageGroup messageGroup : messageGroups) {
            if (group.equals(messageGroup.getGroup())) {
                return messageGroup;
            }
        }
        throw new MyException(ExceptionEnum.NO_GROUP);
    }

}