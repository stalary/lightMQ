/**
 * @(#)TomcatListener.java, 2018-06-18.
 * <p>
 * Copyright 2018 Stalary.
 */
package com.stalary.lightmq;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * TomcatListener
 * 监控容器的开启和关闭状态
 * @author lirongqian
 * @since 2018/06/18
 */
@WebListener
public class TomcatListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("开启容器");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("关闭容器");
    }
}