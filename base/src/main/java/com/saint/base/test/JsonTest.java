package com.saint.base.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.saint.base.util.LocalDateUtil;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2021-01-10 11:35
 */
public class JsonTest {
    public static void main(String[] args) {
        //language=JSON
        String body = "{\"name\": \"xzhou.saint\",\"success\": true, \"result\": [1.1,2.2,3.3,4,5]}\n";
        JSONObject jb = JSONObject.parseObject(body);
        JSONArray result = jb.getJSONArray("result");
        List<Double> list = JSONObject.parseArray(result.toJSONString(), double.class);
        list.stream().forEach(System.out::println);

        LocalDateTime now = LocalDateTime.now();

        LocalDateTime localDateTime = LocalDateUtil.addMinute(now, 3);

        long intervalMillisecond = LocalDateUtil.getIntervalMillisecond(localDateTime, now);
        System.out.println(intervalMillisecond);

    }
}
