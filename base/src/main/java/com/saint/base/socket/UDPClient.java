package com.saint.base.socket;


import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class UDPClient {
    public static void main(String[] args) throws IOException {
        long n = 10000L;
        //新建一个byte数组输出流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeLong(n);
        byte[] buf2 = baos.toByteArray();
        DatagramPacket dp = new DatagramPacket(buf2, buf2.length, new InetSocketAddress("127.0.0.1", 5678));

        /*byte[] buf = (new String("Hello, World!")).getBytes();
        DatagramPacket dp = new DatagramPacket(buf, buf.length,new InetSocketAddress("127.0.0.1", 5678));
*/        //Client端端口号9999向5678端口发送信息。
        DatagramSocket ds = new DatagramSocket(9999);
        ds.send(dp);
        ds.close();
    }
}
