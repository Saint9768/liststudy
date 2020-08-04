package com.saint.base.algorithm;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * LRU算法
 * 基于LinkedHashMap实现
 *
 * @author Saint
 * @createTime 2020-07-01 17:21
 */
public class LRUCache<K, v> extends LinkedHashMap {
    private final int MAX_CACHE_SIZE;

    public LRUCache(int cacheSize) {
        // 使用构造方法 public LinkedHashMap(int initialCapacity, float loadFactor, boolean accessOrder)
        // initialCapacity、loadFactor都不重要
        // accessOrder要设置为true，按访问排序
        super((int) (Math.ceil(cacheSize / 0.75) + 1), 0.75f, true);
        MAX_CACHE_SIZE = cacheSize;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry eldest) {
        // 超过阈值时返回true，进行LRU淘汰
        return size() > MAX_CACHE_SIZE;
    }
}
