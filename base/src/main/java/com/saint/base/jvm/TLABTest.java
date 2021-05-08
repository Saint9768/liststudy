package com.saint.base.jvm;

/**
 * 线程本地分配TLAB测试
 * -XX:-DoEscapeAnalysis -XX:-EliminateAllocations -XX:-UseTLAB
 * 逃逸分析  标量替换  线程专用对象分配
 *
 * @author Saint
 * @version 1.0
 * @createTime 2021-04-17 14:43
 */
public class TLABTest {
    class User {
        int id;
        String name;

        public User(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    void alloc(int i) {
        new User(i, "name " + i);
    }

    public static void main(String[] args) {
        TLABTest t = new TLABTest();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000_0000; i++) {
            t.alloc(i);
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
