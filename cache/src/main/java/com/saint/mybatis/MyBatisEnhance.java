package com.saint.mybatis;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 小数据量的时候，单线程性能更好
 * 500万的数据量测试
 * 单线程：5.5s
 * 并行流-synchronized：3.4s
 * 自旋锁：4.3s/4.2s
 * ConcurrentHashMap：2.7s
 *
 * @author Saint
 * @version 1.0
 * @createTime 2020-09-21 7:19
 */
public class MyBatisEnhance {
    private static final int size = 5000000;

    private static final Map<Object, Object> objectMap = new ConcurrentHashMap<>(size);
    private static final OneSelfLock oneSelfLock = new OneSelfLock();

    @Data
    static class MapperScanner {
        private String mapperLocation;

        public List<Object> scanMapper() {
            List<Object> objects = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                objects.add(new Object());
            }
            return objects;
        }
    }

    public static void put(Object object) {
        objectMap.put(object, object);
    }

    public static void main(String[] args) {
        String mapperLocation = "classpath:mapper/*.xml";
        MapperScanner mapperScanner = new MapperScanner();
        mapperScanner.setMapperLocation(mapperLocation);

        List<Object> classes = mapperScanner.scanMapper();
        long start = System.currentTimeMillis();
        classes.parallelStream().forEach(clazz -> put(new Object()));
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
