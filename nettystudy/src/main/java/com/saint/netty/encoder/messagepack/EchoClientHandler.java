package com.saint.netty.encoder.messagepack;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author Saint
 * @createTime 2020-06-10 22:19
 */
public class EchoClientHandler extends ChannelInboundHandlerAdapter {

    private final int sendNumber;

    public EchoClientHandler(int sendNumber) {
        this.sendNumber = sendNumber;
    }

    private UserInfo[] UserInfo() {
        UserInfo[] userInfos = new UserInfo[this.sendNumber];
        UserInfo userInfo = null;

        for (int i = 0; i < this.sendNumber; ++i) {
            userInfo = new UserInfo();
            userInfo.setAge(i);
            userInfo.setName("ABCDEFG --->" + i);
            userInfos[i] = userInfo;
        }

        return userInfos;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive");
        UserInfo[] infos = this.UserInfo();
        UserInfo[] var3 = infos;
        int var4 = infos.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            UserInfo infoE = var3[var5];
            ctx.writeAndFlush(infoE);
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
