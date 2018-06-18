/**
 * @(#)OperatorService.java, 2018-06-18.
 * <p>
 * Copyright 2018 Stalary.
 */
package com.stalary.lightmq;

import org.springframework.stereotype.Service;

import java.util.HashMap;
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
        Map<String, Map<String, BlockingDeque<MessageDto>>> allQueue = QueueFactory.getAllQueue();
        for (String key : allQueue.keySet()) {
            Map<String, BlockingDeque<MessageDto>> blockingDequeMap = allQueue.getOrDefault(key, new HashMap<>(1));
            BlockingDeque<MessageDto> messageDtos = blockingDequeMap.getOrDefault(topic, null);
            if (messageDtos == null) {
                messageDtos = new LinkedBlockingDeque<>(100);
            }
            messageDtos.offer(new MessageDto(topic, value));
            blockingDequeMap.put(topic, messageDtos);
            allQueue.put(key, blockingDequeMap);
        }
    }

    public MessageDto consume(String group, String topic) {
        BlockingDeque<MessageDto> oneQueue = QueueFactory.getOneQueue(group, topic);
        return oneQueue.poll();
    }
}