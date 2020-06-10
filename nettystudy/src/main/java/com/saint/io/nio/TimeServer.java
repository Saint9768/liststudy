package com.saint.io.nio;

/**
 * @author Saint
 * @createTime 2020-06-10 17:42
 */
public class TimeServer {
    public static void main(String[] args) {
        int port = 8888;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException var3) {
            }
        }
        //MultiplexerTimeServer是一个多路复用类(一个独立的线程)，复杂轮询多路复用器Selector，
        //可以处理多个客户端的并发接入。
        MultiplexerTimeServer timeServer = new MultiplexerTimeServer(port);
        (new Thread(timeServer, "NIO-Server-001")).start();
    }
}
