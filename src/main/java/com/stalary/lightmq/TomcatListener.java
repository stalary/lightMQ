/**
 * @(#)TomcatListener.java, 2018-06-18.
 * <p>
 * Copyright 2018 Stalary.
 */
package com.stalary.lightmq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.stalary.lightmq.data.MessageFactory;
import com.stalary.lightmq.data.SaveData;
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

    /** 当tomcat关闭时，内存中的数据持久化的文件中，下次读取文件 **/
    private static final String FILE_NAME = System.getProperty("user.dir") + "/save.json";

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            // 启动时读取文件中的消息
            File file = new File(FILE_NAME);
            if (file.exists()) {
                String content = FileUtils.readFileToString(file, "UTF-8");
                if (StringUtils.isNotEmpty(content)) {
                    SaveData saveData = JSONObject.parseObject(content, SaveData.class);
                    MessageFactory.groupMap = saveData.getGroupMap();
                    MessageFactory.messageMap = saveData.getMessageMap();
                }
            }
        } catch (IOException e) {
            log.warn("read error.", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        try (FileWriter writer = new FileWriter(FILE_NAME, false)) {
            // 退出时存储消息
            JSONObject.writeJSONString(writer, new SaveData(MessageFactory.groupMap, MessageFactory.messageMap));
        } catch (IOException e) {
            log.warn("writer error.", e);
        }
    }
}