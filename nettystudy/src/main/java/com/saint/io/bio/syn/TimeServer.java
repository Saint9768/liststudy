package com.saint.io.bio.syn;

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
            } catch (NumberFormatException var12) {
            }
        }

        //监听端口号
        ServerSocket server = null;

        try {
            server = new ServerSocket(port);
            System.out.println("The time server is start in port :" + port);
            Socket socket = null;

            while (true) {
                //BIO的accept和读写都阻塞
                socket = server.accept();
                (new Thread(new TimeServerHandler(socket))).start();
            }
        } catch (Exception var13) {
            var13.printStackTrace();
        } finally {
            if (server != null) {
                System.out.println("The time server is close!");

                try {
                    server.close();
                } catch (IOException var11) {
                    var11.printStackTrace();
                }

                server = null;
            }

        }

    }
}
