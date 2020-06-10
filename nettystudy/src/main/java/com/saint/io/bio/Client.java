package com.saint.io.bio;

import java.io.IOException;
import java.net.Socket;

/**
 * @author Saint
 * @createTime 2020-06-10 11:33
 */
public class Client {
    public static void main(String[] args) throws IOException {
        Socket s = new Socket("127.0.0.1", 8888);
        s.getOutputStream().write("Hello World!".getBytes());
        s.getOutputStream().flush();
        System.out.println("Write over, waiting for msg back!");
        byte[] bytes = new byte[1024];
        int len = s.getInputStream().read(bytes);
        System.out.println(new String(bytes, 0, len));
        s.close();
    }
}
