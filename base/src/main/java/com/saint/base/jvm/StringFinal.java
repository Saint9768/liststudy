package com.saint.base.jvm;

/**
 * @author Saint
 * @createTime 2020-04-01 13:15
 */
public class StringFinal {
    public static void main(String[] args) {
        //这里的s指向一个String对象,String对象时不可变的，输出为 苹果
        String s = "苹果";
        modify(s);
        System.out.println(s);

        //这里的s1指向的字符串常量池地址不变。答案仍为 菠萝
        String s1 = "菠萝";
        modify(s1);
        System.out.println(s1);

    }

    public static void modify(String s) {
        s = s + "好吃";
    }
}
