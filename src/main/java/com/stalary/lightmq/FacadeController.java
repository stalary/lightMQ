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
        service.produce(topic, value);
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

    @GetMapping("/register")
    public JsonResponse register(
            @RequestParam String group) {
        service.registerGroup(group);
        return JsonResponse.success();
    }
}