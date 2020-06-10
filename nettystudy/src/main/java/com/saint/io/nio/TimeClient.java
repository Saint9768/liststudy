package com.saint.io.nio;

/**
 * @author Saint
 * @createTime 2020-06-10 18:02
 */
public class TimeClient {
    public static void main(String[] args) {
        int port = 8888;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException var3) {
            }
        }
        //TimeClientHandle线程用来处理连接和读写操作
        (new Thread(new TimeClientHandle("127.0.0.1", port), "NIO-client-001")).start();
    }
}
