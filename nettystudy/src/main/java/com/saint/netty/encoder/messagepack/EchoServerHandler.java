package com.saint.netty.encoder.messagepack;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Iterator;
import java.util.List;

/**
 * @author Saint
 * @createTime 2020-06-10 22:29
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    private int counter = 0;

    public EchoServerHandler() {
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        List<Object> users = (List) msg;
        Iterator var4 = users.iterator();

        while (var4.hasNext()) {
            Object user = var4.next();
            System.out.println("用户： " + user);
        }

        System.out.println("counter : " + ++this.counter);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
