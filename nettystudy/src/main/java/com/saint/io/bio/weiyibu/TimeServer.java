package com.saint.io.bio.weiyibu;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Saint
 * @createTime 2020-06-10 17:27
 */
public class TimeServer {
    public static void main(String[] args) {
        int port = 8888;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException var13) {
            }
        }
        ServerSocket server = null;
        try {
            server = new ServerSocket(port);
            System.out.println("The time server is start in port :" + port);
            Socket socket = null;
            //伪异步，使用线程池来处理请求
            //当接收到新的客户端连接时，将请求Socket封装成一个Task，然后调用线程池的execute方法执行
            //从而避免了每个请求接入都要创建一个新的线程
            TimeServerHandlerExecutePool timeExecutePool = new TimeServerHandlerExecutePool(50, 10000);
            while (true) {
                socket = server.accept();
                timeExecutePool.execute(new TimeServerHandler(socket));
            }
        } catch (Exception var14) {
            var14.printStackTrace();
        } finally {
            if (server != null) {
                System.out.println("The time server is close!");
                try {
                    server.close();
                } catch (IOException var12) {
                    var12.printStackTrace();
                }
                server = null;
            }
        }
    }
}
