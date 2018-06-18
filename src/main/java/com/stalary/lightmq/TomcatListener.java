/**
 * @(#)TomcatListener.java, 2018-06-18.
 * <p>
 * Copyright 2018 Stalary.
 */
package com.stalary.lightmq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * TomcatListener
 * 监控容器的开启和关闭状态
 * @author lirongqian
 * @since 2018/06/18
 */
@WebListener
@Slf4j
public class TomcatListener implements ServletContextListener {

    private static final String FILE_NAME = System.getProperty("user.dir") + "/save.json";

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            String content= FileUtils.readFileToString(new File(FILE_NAME), "UTF-8");
            if (StringUtils.isNotEmpty(content)) {
                MessageList messageList = JSON.parseObject(content, MessageList.class);
                QueueFactory.setAllQueue(messageList.getMessageList());
            }
        } catch (IOException e) {
            log.warn("read error.", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        try {
            FileWriter writer = new FileWriter(FILE_NAME, false);
            JSONObject.writeJSONString(writer, new MessageList(QueueFactory.getAllQueue()));
            writer.close();
        } catch (IOException e) {
            log.warn("writer error.", e);
        }
    }
}