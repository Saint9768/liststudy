package com.saint.spring.cycle;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2021-03-23 21:41
 */
public class CycleTest {
    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("cycle.xml");
        A a = ac.getBean(A.class);
        System.out.println(a.getB());
        B b = ac.getBean(B.class);
        System.out.println(b.getA());
    }
}
