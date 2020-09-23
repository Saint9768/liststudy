package com.saint.base.stream;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * stream用于解决已有集合类库既有的弊端，其只是集合元素的函数模型
 * Stream操作的两个基本的特征：1.Pipelining：中间操作都会返回流对象本身。
 * 2. 内部迭代：以前对集合遍历都是通过Iterator或者增强for的形式。Stream提供了内部迭代的方式，溜可以直接调用遍历方法。
 * 注：只能对流进行一次终结操作，或多次延迟操作 -- 链式调用。
 *
 * @author Saint
 * @createTime 2020-06-27 22:13
 */
public class StreamTest {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("周恩来");
        list.add("毛泽东");
        list.add("朱德");
        list.add("周小平");
        list.add("周毛");

        //stream filter,获取流、过滤周姓、过滤长度为3，逐一打印。
        list.stream().filter(s -> s.startsWith("周"))
                .filter(s -> s.length() == 3)
                .forEach(System.out::println);

        long count = list.stream().filter(s -> s.startsWith("周"))
                .filter(s -> s.length() == 3).count();
        System.out.println(count);

        //并行流
        list.parallelStream().forEach(System.out::println);

        // 1.1 map()，将mapper应用于当前流中所有元素所产生的结果。
        list.stream().map(s -> s.substring(0, 1)).forEach(System.out::println);

        //2. 获取流
        //2.1. 根据Collection获取流
        Stream<String> stream1 = list.stream();
        //2.2. 根据Map获取流
        Map<String, String> map = new HashMap<>();
        Stream<String> keyStream = map.keySet().stream();
        Stream<String> valueStream = map.values().stream();
        Stream<Map.Entry<String, String>> entryStream = map.entrySet().stream();
        //2.3. 根据数组获取流
        String[] array = {"hello", "sainta", "and", "worldaa", "hello"};
        Stream<String> stringStream = Stream.of(array);
        stringStream.forEach(name -> System.out.println(name));

        //2.3.2 取数组的一部分
        Stream<String> stream = Arrays.stream(array, 1, 2);
        stream.forEach(System.out::println);
        System.out.println("#########");

        // 2.4 剔除流中重复的元素
        Stream<String> dupli = Stream.of(array).distinct();
        dupli.forEach(name -> System.out.println(name));
        System.out.println("#########");

        // 2.5 对流中的数据进行排序(根据文本长度，逆序排序）
        Stream.of(array).sorted(Comparator.comparing(String::length).reversed()).forEach(System.out::println);
        System.out.println("#####AAAAAA");

        //2.6 peek获取流生成一个新流，并在获取每个元素时，调用action
        Object[] peekString = Stream.of(array).peek(System.out::println).limit(3).toArray();

        Optional<String> first = list.stream().filter(s -> s.startsWith("周")).findFirst();
        System.out.println(first.get());

        //2.7. Optional.anyMacth()方法,有任意元素匹配
        boolean anyMatch = list.stream().anyMatch(s -> s.startsWith("周"));
        System.out.println("anyMatch:" + anyMatch);

        // 2.8 将流的结果用List接收
        List<String> streamList = list.stream().collect(Collectors.toList());
        System.out.println(streamList);

        IntSummaryStatistics summary = Stream.of(array).collect(Collectors.summarizingInt(String::length));
        double average = summary.getAverage();
        double max = summary.getMax();
        System.out.println(average + " , " + max);

        //3. some methods
        /*//3.1. limit 对流进行截取，只取前N个
        Stream<String> limit = stringStream.limit(2);
        System.out.println("After limit :" + limit.count());

        //3.2 skip 跳过前N个元素
        Stream<String> skip = stringStream.skip(2);
        System.out.println("After skip :" + skip.count());

        //3.3. 组合两个流，concat
        Stream<String> streamA = Stream.of("Bob");
        Stream<String> streamB = Stream.of("Saint");
        Stream<String> concat = Stream.concat(streamA, streamB);
        System.out.println(concat.count());*/

    }
}
