/**
 * @(#)OperatorService.java, 2018-06-18.
 * <p>
 * Copyright 2018 Stalary.
 */
package com.stalary.lightmq;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public void produce(String topic, String value) {
        // 将每一个消费者组都进行修改
        List<Message> allQueue = QueueFactory.getAllQueue();
        for (Message message : allQueue) {
            Map<String, BlockingDeque<MessageDto>> blockingDequeMap = message.getMessage();
            BlockingDeque<MessageDto> messageDtos = blockingDequeMap.getOrDefault(topic, null);
            if (messageDtos == null) {
                messageDtos = new LinkedBlockingDeque<>(100);
            }
            messageDtos.offer(new MessageDto(topic, value));
            blockingDequeMap.put(topic, messageDtos);
            message.setMessage(blockingDequeMap);
        }
    }

    public MessageDto consume(String group, String topic) {
        BlockingDeque<MessageDto> oneQueue = QueueFactory.getOneQueue(group, topic);
        return oneQueue.poll();
    }

    public void registerGroup(String group) {
        List<Message> allQueue = QueueFactory.getAllQueue();
        Message message = new Message();
        message.setGroup(group);
        allQueue.add(message);
    }
}