package com.saint.base.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2021-01-10 9:36
 */
public class CollectionUtil {

    /**
     * map转换为list
     *
     * @param map
     * @return
     */
    public static List<Object> toList(Map<String, Object> map) {
        List<Object> list = new ArrayList<>();
        Set<Map.Entry<String, Object>> entries = map.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            list.add(entry.getKey());
            list.add(entry.getValue());
        }
        return list;
    }
}
