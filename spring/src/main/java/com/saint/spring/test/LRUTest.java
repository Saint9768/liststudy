package com.saint.spring.test;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2021-03-21 15:03
 */
public class LRUTest extends LinkedHashMap {

    private final int CACHE_SIZE;

    public LRUTest(int cacheSize) {
        super((int) Math.ceil(cacheSize / 0.75) + 1, 0.75f, true);
        this.CACHE_SIZE = cacheSize;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry eldest) {
        //当map中的数据量大于指定的缓存个数的时候，就自动删除最老的数据
        return size() > CACHE_SIZE;
    }
}
