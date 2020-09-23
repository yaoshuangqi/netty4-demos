package com.yaosq.netty.codec.coder;

import com.yaosq.netty.codec.entity.Msg;
import com.yaosq.netty.codec.entity.MsgHeader;
import com.yaosq.netty.codec.entity.MsgType;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.Charset;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description 自定义编码器
 * @createtime 2020/9/23 20:09
 */
public class SignEncoder extends MessageToByteEncoder<Msg> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Msg msg, ByteBuf byteBuf) throws Exception {

        if(msg == null || msg.getMsgHeader() == null){
            System.out.println("==> msg or msg header is null");
            return;
        }
        String msgBody = msg.getBody();
        MsgHeader header = msg.getMsgHeader();

        byte[] bytes = msgBody.getBytes(Charset.forName("utf-8"));

        //按照协议 编码
        byteBuf.writeByte(header.getMsgType());
        byteBuf.writeInt(header.getLen());
        byteBuf.writeBytes(bytes);

    }
}
