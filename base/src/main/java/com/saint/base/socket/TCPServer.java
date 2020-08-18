package com.saint.base.socket;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    public static void main(String[] args) {
        //新建一个端口号为6666Socket服务端
        Socket s = null;
        DataInputStream dis = null;
        try {
            ServerSocket ss = new ServerSocket(6666);
            //连接一个打印一个
            while (true) {
                //接收客户端的连接
                //accept()、readUTF()都是阻塞式的。
                s = ss.accept();
                System.out.println("A client connect!");
                //获取管道中客户端输入的信息
                dis = new DataInputStream(s.getInputStream());
                System.out.println(dis.readUTF());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                dis.close();
                s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
