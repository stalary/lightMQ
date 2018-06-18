/**
 * @(#)OperatorService.java, 2018-06-18.
 * <p>
 * Copyright 2018 Stalary.
 */
package com.stalary.lightmq;

import com.google.common.collect.Lists;
import com.stalary.lightmq.exception.ExceptionEnum;
import com.stalary.lightmq.exception.MyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * OperatorService
 *
 * @author lirongqian
 * @since 2018/06/18
 */
@Service
public class OperatorService {

    public void produce(String topic, String key, String value) {
        // 将每一个消费者组都进行修改
        List<Message> allQueue = QueueFactory.getAllQueue();
        for (Message message : allQueue) {
            // 查找对应的topic
            if (topic.equals(message.getTopic())) {
                // 通知所有消费组
                List<MessageGroup> messageGroup = message.getMessageGroup();
                for (MessageGroup group : messageGroup) {
                    LinkedBlockingDeque<MessageDto> temp = group.getMessage();
                    temp.add(new MessageDto(topic, key, value));
                    group.setMessage(temp);
                }
                message.setMessageGroup(messageGroup);
                return;
            }
        }
        throw new MyException(ExceptionEnum.NO_TOPIC);
    }

    public MessageDto consume(String group, String topic) {
        BlockingDeque<MessageDto> oneQueue = QueueFactory.getOneQueue(group, topic);
        return oneQueue.poll();
    }

    public void registerGroup(String group, String topic) {
        Message message = QueueFactory.getOneMessage(topic);
        if (topic == null) {
            throw new MyException(ExceptionEnum.NO_TOPIC);
        }
        message.getMessageGroup().add(new MessageGroup(group, new LinkedBlockingDeque<>(100)));
    }

    public void registerTopic(String topic) {
        QueueFactory.getAllQueue().add(new Message(topic, Lists.newArrayList(new MessageGroup(QueueFactory.DEFAULT_GROUP, new LinkedBlockingDeque<>(100)))));
    }
}