package com.saint.netty.encoder.messagepack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.msgpack.MessagePack;

/**
 * 二进制序列化框架
 * 负载将Object类型的POJO的对象编码为byte数组，然后写入到ByteBuf中
 *
 * @author Saint
 * @createTime 2020-06-10 22:14
 */
public class MsgpackEncoder extends MessageToByteEncoder<Object> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        MessagePack messagePack = new MessagePack();
        byte[] raw = messagePack.write(msg);
        out.writeBytes(raw);
    }
}
