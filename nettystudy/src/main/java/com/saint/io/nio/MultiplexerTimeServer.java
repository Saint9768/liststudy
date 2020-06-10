package com.saint.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Saint
 * @createTime 2020-06-10 17:46
 */
public class MultiplexerTimeServer implements Runnable {
    private Selector selector;
    private ServerSocketChannel serverChannerl;
    private volatile boolean stop;

    /**
     * 进行资源初始化
     * 创建多路复用器Selector、ServerSocketChannel
     * 对Channel和TCP参数进行配置
     *
     * @param port
     */
    public MultiplexerTimeServer(int port) {
        try {
            this.selector = Selector.open();
            this.serverChannerl = ServerSocketChannel.open();
            //将ServerSocketChannel设为异步非阻塞模式
            this.serverChannerl.configureBlocking(false);
            this.serverChannerl.socket().bind(new InetSocketAddress(port), 1024);
            //将ServerSocketChannel注册到Selector中,蒋婷SelectionKey.OP_ACCEPT操作位
            this.serverChannerl.register(this.selector, SelectionKey.OP_ACCEPT);
            System.out.println("The time server is start in port : " + port);
        } catch (Exception var3) {
            var3.printStackTrace();
            System.exit(1);
        }

    }

    public void stop() {
        this.stop = true;
    }

    @Override
    public void run() {
        //遍历Selector
        while (!this.stop) {
            try {
                //Selector每个一秒唤醒一次
                this.selector.select(1000L);
                Set<SelectionKey> selectedKeys = this.selector.selectedKeys();
                Iterator<SelectionKey> it = selectedKeys.iterator();
                SelectionKey key = null;

                while (it.hasNext()) {
                    key = it.next();
                    it.remove();

                    try {
                        this.handleInput(key);
                    } catch (Exception var6) {
                        if (key != null) {
                            key.cancel();
                            if (key.channel() != null) {
                                key.channel().close();
                            }
                        }
                    }
                }
            } catch (Exception var7) {
                var7.printStackTrace();
            }
        }

        //多路复用器关闭后，所有注册在上面的Channel和Pipe等资源都会被自动注册并关闭，所以不需要重复释放资源
        if (this.selector != null) {
            try {
                this.selector.close();
            } catch (IOException var5) {
                var5.printStackTrace();
            }
        }

    }

    private void handleInput(SelectionKey key) throws IOException {
        if (key.isValid()) {
            //处理新接入的客户端请求消息
            if (key.isAcceptable()) {
                //下卖弄是TCP的三次握手操作
                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                SocketChannel sc = ssc.accept();
                sc.configureBlocking(false);
                sc.register(this.selector, 1);
            }
            //读取客户端的请求信息
            if (key.isReadable()) {
                SocketChannel sc = (SocketChannel) key.channel();
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                //调用SocketChannel的read方法去读请求码流
                int readBytes = sc.read(readBuffer);
                //读到了字节，对字节进行编解码
                if (readBytes > 0) {
                    readBuffer.flip();
                    byte[] bytes = new byte[readBuffer.remaining()];
                    readBuffer.get(bytes);
                    String body = new String(bytes, StandardCharsets.UTF_8);
                    System.out.println("The time server receive order : " + body);
                    String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? (new Date(System.currentTimeMillis())).toString() : "BAD ORDER";
                    this.doWrite(sc, currentTime);
                    //链路已经关闭，关闭SocketChannel，释放资源
                } else if (readBytes < 0) {
                    key.cancel();
                    sc.close();
                }
            }
        }

    }

    /**
     * 将应答消息异步发送给客户端
     *
     * @param sc
     * @param response
     * @throws IOException
     */
    private void doWrite(SocketChannel sc, String response) throws IOException {
        if (response != null && response.trim().length() > 0) {
            byte[] bytes = response.getBytes();
            ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
            writeBuffer.put(bytes);
            //如果不对ByteBuffer执行flip操作，可能会出现写半包问题。
            writeBuffer.flip();
            sc.write(writeBuffer);
        }
    }
}
