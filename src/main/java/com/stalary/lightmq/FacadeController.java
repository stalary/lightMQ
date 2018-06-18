/**
 * @(#)FacadeController.java, 2018-06-18.
 * <p>
 * Copyright 2018 Stalary.
 */
package com.stalary.lightmq;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * FacadeController
 *
 * @author lirongqian
 * @since 2018/06/18
 */
@RestController
public class FacadeController {

    @Resource
    private OperatorService service;

    @GetMapping("/produce")
    public JsonResponse produce(
            @RequestParam String topic,
            @RequestParam(required = false, defaultValue = "") String key,
            @RequestParam String value) {
        service.produce(topic, key, value);
        return JsonResponse.success();
    }

    @GetMapping("/consume")
    public JsonResponse consume(
            @RequestParam(required = false, defaultValue = QueueFactory.DEFAULT_GROUP) String group,
            @RequestParam String topic) {
        return JsonResponse.success(service.consume(group, topic));
    }

    @GetMapping("/getAll")
    public JsonResponse getAll() {
        return JsonResponse.success(QueueFactory.getAllQueue());
    }

    /**
     * 获取所有topic
     * @return
     */
    @GetMapping("/getAllTopic")
    public JsonResponse getAllTopic() {
        List<String> topicList = QueueFactory.getAllQueue().stream().map(Message::getTopic).collect(Collectors.toList());
        return JsonResponse.success(topicList);
    }

    /**
     * 注册group
     * @param group
     * @param topic
     * @return
     */
    @GetMapping("/registerGroup")
    public JsonResponse registerGroup(
            @RequestParam String group,
            @RequestParam String topic) {
        service.registerGroup(group, topic);
        return JsonResponse.success("注册group成功");
    }

    @GetMapping("/registerTopic")
    public JsonResponse registerTopic(
            @RequestParam String topic) {
        service.registerTopic(topic);
        return JsonResponse.success("注册topic成功");
    }
}