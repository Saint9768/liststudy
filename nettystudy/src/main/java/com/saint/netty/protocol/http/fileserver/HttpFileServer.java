package com.saint.netty.protocol.http.fileserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author Saint
 * @createTime 2020-06-13 13:49
 */
public class HttpFileServer {

    private static final String DEFAULT_UTL = "/src/com/saint/netty/";

    public void run(final int port, final String url) throws Exception {
        EventLoopGroup boosGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(boosGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //请求消息解码器
                            ch.pipeline().addLast("http-decoder", new HttpRequestDecoder());
                            //将多个消息转换为单一的request或者response对象。
                            ch.pipeline().addLast("http-aggregatot", new HttpObjectAggregator(65536));
                            //响应解码器
                            ch.pipeline().addLast("http-endoer", new HttpRequestEncoder());
                            //支持最大异步文件传输
                            ch.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
                            //业务逻辑
                            ch.pipeline().addLast("fileServerHandler", new HttpFileServerHandler(url));
                        }
                    });
            ChannelFuture future = b.bind("192.168.240.1", port).sync();
            System.out.println("HTTP 文件目录服务器启动，网址是 ：" + "http://192.168.240.1:" + port + url);
            future.channel().closeFuture().sync();
        } finally {
            boosGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 8080;
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        String url = DEFAULT_UTL;
        if (args.length > 1) {
            url = args[1];
        }
        new HttpFileServer().run(port, url);
    }

}
