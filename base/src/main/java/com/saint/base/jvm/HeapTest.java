package com.saint.base.jvm;

import java.util.ArrayList;

public class HeapTest {

    byte[] a = new byte[1024 * 1000];

    public static void main(String[] args) throws InterruptedException {
        ArrayList<HeapTest> heapTests = new ArrayList<>();
        while (true) {
            heapTests.add(new HeapTest());
            Thread.sleep(10);
        }
    }
}
