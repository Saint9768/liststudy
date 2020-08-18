package com.saint.base.socket;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPServer {
    public static void main(String[] args) throws IOException {
        //用于存储接收的数据
        byte[] buf = new byte[1024];
        //数据包
        DatagramPacket dp = new DatagramPacket(buf, buf.length);
        DatagramSocket ds = new DatagramSocket(5678);
        while (true) {
            //获取到数据包，接着数据包的全部数据
            ds.receive(dp);
            //System.out.println(new String(buf, 0, buf.length));
            ByteArrayInputStream bais = new ByteArrayInputStream(buf);
            DataInputStream dis = new DataInputStream(bais);
            System.out.println(dis.readLong());
        }


    }
}
