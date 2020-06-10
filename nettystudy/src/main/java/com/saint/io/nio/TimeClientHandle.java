package com.saint.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Saint
 * @createTime 2020-06-10 18:03
 */
public class TimeClientHandle implements Runnable {
    private String host;
    private int port;
    private Selector selector;
    private SocketChannel socketChannel;
    private volatile boolean stop;

    /**
     * 用来初始化NIO的多路复用器和SocketChannel对象
     *
     * @param host
     * @param port
     */
    public TimeClientHandle(String host, int port) {
        this.host = host == null ? "127.0.0.1" : host;
        this.port = port;
        try {
            this.selector = Selector.open();
            this.socketChannel = SocketChannel.open();
            this.socketChannel.configureBlocking(false);
        } catch (IOException var4) {
            var4.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void run() {
        try {
            //发送连接请求
            this.doConnect();
        } catch (IOException var6) {
            var6.printStackTrace();
            System.exit(1);
        }

        while (!this.stop) {
            try {
                this.selector.select(1000L);
                Set<SelectionKey> selectedKeys = this.selector.selectedKeys();
                Iterator<SelectionKey> it = selectedKeys.iterator();
                SelectionKey key = null;

                while (it.hasNext()) {
                    key = it.next();
                    it.remove();

                    try {
                        this.handleInput(key);
                    } catch (Exception var7) {
                        if (key != null) {
                            key.cancel();
                            if (key.channel() != null) {
                                key.channel().close();
                            }
                        }
                    }
                }
            } catch (Exception var8) {
                var8.printStackTrace();
                System.exit(1);
            }
        }

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
            //判断是否连接成功
            SocketChannel sc = (SocketChannel) key.channel();
            if (key.isConnectable()) {
                if (sc.finishConnect()) {
                    sc.register(this.selector, SelectionKey.OP_READ);
                    //发送消息给服务端
                    this.doWrite(sc);
                } else {
                    //连接失败，进程退出
                    System.exit(1);
                }
            }
            //读取服务端的应答消息
            if (key.isReadable()) {
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                //进行异步读取操作
                int readBytes = sc.read(readBuffer);
                if (readBytes > 0) {
                    readBuffer.flip();
                    byte[] bytes = new byte[readBuffer.remaining()];
                    readBuffer.get(bytes);
                    String body = new String(bytes, StandardCharsets.UTF_8);
                    System.out.println("Now is : " + body);
                    this.stop = true;
                } else if (readBytes < 0) {
                    key.cancel();
                    sc.close();
                }
            }
        }

    }

    private void doConnect() throws IOException {
        //如果直接连接成功，则注册到多路复用器上，发送请求消息，读应答。
        if (this.socketChannel.connect(new InetSocketAddress(this.host, this.port))) {
            this.socketChannel.register(this.selector, SelectionKey.OP_READ);
            this.doWrite(this.socketChannel);
        } else {
            this.socketChannel.register(this.selector, SelectionKey.OP_CONNECT);
        }

    }

    /**
     * 发送消息给服务端
     *
     * @param sc
     * @throws IOException
     */
    private void doWrite(SocketChannel sc) throws IOException {
        byte[] req = "QUERY TIME ORDER".getBytes();
        ByteBuffer writeBuffer = ByteBuffer.allocate(req.length);
        writeBuffer.put(req);
        writeBuffer.flip();
        sc.write(writeBuffer);
        if (!writeBuffer.hasRemaining()) {
            System.out.println("Send  order 2 server succeed.");
        }

    }
}
