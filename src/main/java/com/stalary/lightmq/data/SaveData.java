/**
 * @(#)SaveData.java, 2018-11-24.
 *
 * Copyright 2018 Stalary.
 */
package com.stalary.lightmq.data;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * SaveData
 *
 * @author lirongqian
 * @since 2018/11/24
 */
@Data
@AllArgsConstructor
public class SaveData {

    /** topic:group的映射 **/
    private Map<String, List<MessageGroup>> groupMap;

    /** topic:(offset:message)的映射 **/
    private Map<String, Map<Long, MessageDto>> messageMap;
}