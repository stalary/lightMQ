/**
 * @(#)MessageGroup.java, 2018-06-18.
 * <p>
 * Copyright 2018 Stalary.
 */
package com.stalary.lightmq.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.LinkedBlockingDeque;

/**
 * MessageGroup
 * 消息组
 * @author lirongqian
 * @since 2018/06/18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageGroup {

    /** 默认分组为master **/
    private String group = Constant.DEFAULT_GROUP;

    /** 起始offset为0 **/
    private Long offset = 0L;

    public void incOffset() {
        offset++;
    }
}