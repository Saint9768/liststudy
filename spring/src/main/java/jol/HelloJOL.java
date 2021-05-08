package jol;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2021-03-26 7:50
 */
public class HelloJOL {
    public static void main(String[] args) throws InterruptedException {
//        Thread.sleep(5000);
        Object o = new Object();
        System.out.println(ClassLayout.parseInstance(o).toPrintable());
        synchronized (o) {
            System.out.println(ClassLayout.parseInstance(o).toPrintable());
        }
    }
}
