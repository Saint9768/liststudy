package com.saint.netty.encoder.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Saint
 * @createTime 2020-06-10 22:47
 */
public class TestSubscribeReqProto {

    private static byte[] encode(SubscribeReqProto.SubscribeReq req) {
        return req.toByteArray();
    }

    private static SubscribeReqProto.SubscribeReq decode(byte[] body) throws InvalidProtocolBufferException {
        return SubscribeReqProto.SubscribeReq.parseFrom(body);
    }

    private static SubscribeReqProto.SubscribeReq createSubscribeReq() {
        SubscribeReqProto.SubscribeReq.Builder builder = SubscribeReqProto.SubscribeReq.newBuilder();
        builder.setSubReqId(1);
        builder.setUserName("Saint");
        builder.setProductName("Netty Book");
        List<String> address = new ArrayList();
        address.add("NanJin JianYe");
        address.add("BeiJin ErHuan");
        address.add("ShenZhen HongShuLin");
        builder.setAddress(address.toString());
        return builder.build();
    }

    public static void main(String[] args) throws InvalidProtocolBufferException {
        SubscribeReqProto.SubscribeReq req = createSubscribeReq();
        System.out.println("Before encode : " + req.toString());
        SubscribeReqProto.SubscribeReq req2 = decode(encode(req));
        System.out.println("After decode : " + req.toString());
        //序列化后的对象和原始对象是一样的
        System.out.println("Assert equal : --> " + req2.equals(req));
    }
}
