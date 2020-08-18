package com.saint.base.jvm;

/**
 * @author Saint
 * @createTime 2020-03-02 11:00
 */
public class Test {
    public static void main(String[] args) {

        String str1 = new StringBuilder("计算机").append("软件").toString();

        //String str1 = new StringBuilder("计算机软件").toString();
        //变量s1保存在栈中，
        //String s1 = new String("计算机软件");

        //不新建一个s1变量时，为true，新建s1变量之后，“计算机软件”该字符串已经存在在堆中的引用。
        System.out.println(str1.intern() == str1);

        //false
        //System.out.println(s1 == s1.intern());

        // true，intern()返回字符串在堆中的第一次引用
        //System.out.println(s1.intern() == str1.intern());

    }

}
