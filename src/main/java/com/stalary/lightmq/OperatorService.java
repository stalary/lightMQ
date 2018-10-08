/**
 * @(#)OperatorService.java, 2018-06-18.
 * <p>
 * Copyright 2018 Stalary.
 */
package com.stalary.lightmq;

import com.google.common.collect.Lists;
import com.stalary.lightmq.data.Message;
import com.stalary.lightmq.data.MessageDto;
import com.stalary.lightmq.data.MessageGroup;
import com.stalary.lightmq.exception.ExceptionEnum;
import com.stalary.lightmq.exception.MyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * OperatorService
 *
 * @author lirongqian
 * @since 2018/06/18
 */
@Service
@Slf4j
public class OperatorService {

    /**
     * 服务器性能较差，只允许最多申请10个线程
     */
    private ExecutorService executor = Executors.newFixedThreadPool(10);

    /**
     * 同步生产消息
     *
     * @param topic
     * @param key
     * @param value
     */
    public void produceSyn(String topic, String key, String value) {
        // 将每一个消费者组都进行修改
        List<Message> allQueue = QueueFactory.getAllQueue();
        for (Message message : allQueue) {
            // 查找对应的topic
            if (topic.equals(message.getTopic())) {
                // 通知所有消费组
                List<MessageGroup> messageGroup = message.getMessageGroup();
                for (MessageGroup group : messageGroup) {
                    LinkedBlockingDeque<MessageDto> temp = group.getMessage();
                    temp.offer(new MessageDto(topic, key, value));
                    group.setMessage(temp);
                }
                message.setMessageGroup(messageGroup);
                return;
            } else {
                throw new MyException(ExceptionEnum.NO_TOPIC);
            }
        }
    }

    /**
     * 异步生产消息,不保证消息的顺序
     */
    public void produceAsyn(String topic, String key, String value) {
        // 将每一个消费者组都进行修改
        List<Message> allQueue = QueueFactory.getAllQueue();
        for (Message message : allQueue) {
            // 查找对应的topic
            if (topic.equals(message.getTopic())) {
                // 开启异步任务
                executor.execute(() -> {
                    // 通知所有消费组
                    List<MessageGroup> messageGroup = message.getMessageGroup();
                    for (MessageGroup group : messageGroup) {
                        Thread.currentThread().setName("topic:" + topic + "&group:" + group);
                        LinkedBlockingDeque<MessageDto> temp = group.getMessage();
                        temp.offer(new MessageDto(topic, key, value));
                        group.setMessage(temp);
                    }
                });
                return;
            } else {
                throw new MyException(ExceptionEnum.NO_TOPIC);
            }
        }
    }

    /**
     * 默认非阻塞进行消费
     *
     * @param group
     * @param topic
     * @param block
     * @return
     */
    public MessageDto consume(String group, String topic, boolean block) {
        BlockingDeque<MessageDto> oneQueue = QueueFactory.getOneQueue(group, topic);
        if (block) {
            try {
                return oneQueue.take();
            } catch (InterruptedException e) {
                log.warn("consume error", e);
            }
        } else {
            return oneQueue.poll();
        }
        return null;
    }

    /**
     * 注册分组
     *
     * @param group 分组
     * @param topic
     */
    public void registerGroup(String group, String topic) {
        Message message = QueueFactory.getOneMessage(topic);
        // topic还未注册
        if (topic == null) {
            throw new MyException(ExceptionEnum.NO_TOPIC);
        }
        List<MessageGroup> messageGroup = message.getMessageGroup();
        messageGroup.forEach(g -> {
            if (g.getGroup().equals(group)) {
                // 分组申请重复
                throw new MyException(ExceptionEnum.REPEAT_GROUP);
            }
        });
        message.getMessageGroup().add(new MessageGroup(group, new LinkedBlockingDeque<>(100)));
    }

    /**
     * 注册topic
     *
     * @param topic
     */
    public void registerTopic(String topic) {
        List<Message> allQueue = QueueFactory.getAllQueue();
        allQueue.forEach(message -> {
            if (message.getTopic().equals(topic)) {
                // topic申请重复
                throw new MyException(ExceptionEnum.REPEAT_TOPIC);
            }
        });
        allQueue.add(new Message(topic, Lists.newArrayList(new MessageGroup(QueueFactory.DEFAULT_GROUP, new LinkedBlockingDeque<>(100)))));
    }
}