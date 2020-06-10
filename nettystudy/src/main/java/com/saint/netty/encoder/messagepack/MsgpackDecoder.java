package com.saint.netty.encoder.messagepack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.msgpack.MessagePack;

import java.util.List;

/**
 * @author Saint
 * @createTime 2020-06-10 22:12
 */
public class MsgpackDecoder extends MessageToMessageDecoder<ByteBuf> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        int length = msg.readableBytes();
        byte[] array = new byte[length];
        //从msg中获取需要解码的byte对象
        msg.getBytes(msg.readerIndex(), array, 0, length);
        MessagePack messagePack = new MessagePack();
        //调用MessagePack的read方法将其反序列化为Object对象
        out.add(messagePack.read(array));
    }
}
