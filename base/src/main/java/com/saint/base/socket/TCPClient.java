package com.saint.base.socket;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class TCPClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        //建立连接到server端的客户端连接，客户端的端口是随机的。
        Socket s = new Socket("127.0.0.1", 6666);
        //Socket中通过管道或者流通信。
        OutputStream os = s.getOutputStream();
        DataOutputStream dos = new DataOutputStream(os);
        Thread.sleep(3000);
        dos.writeUTF("hello server!");
        dos.flush();
        dos.close();
        s.close();
    }
}
