package com.saint.netty.frame.fixLen;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author Saint
 * @createTime 2020-06-10 21:56
 */
public class EchoClientHandler extends ChannelInboundHandlerAdapter {

    private int counter;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("This is : " + ++this.counter + " ; time receive sever : [" + msg + "]");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 10; ++i) {
            ctx.writeAndFlush(Unpooled.copiedBuffer("Welcome to Netty".getBytes()));
        }

    }
}
