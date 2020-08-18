package com.saint.base.jvm;

public class Jvm1 {

    public int compute() {
        int a = 1;
        int b = 2;
        int c = (a + b) * 10;
        return c;
    }

    public static void main(String[] args) {
        Jvm1 math = new Jvm1();
        math.compute();
        System.out.println("test");
    }
}
