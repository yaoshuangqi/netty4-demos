package com.yaosq.netty.codec.coder;

import com.yaosq.netty.codec.entity.Msg;
import com.yaosq.netty.codec.entity.MsgHeader;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description 自定义解码器
 * @createtime 2020/9/23 20:09
 */
public class SignDecoder extends LengthFieldBasedFrameDecoder {
    private static final int MAX_FRAME_LENGTH = 1024 * 1024;
    private static final int LENGTH_FIELD_LENGTH = 1;
    private static final int LENGTH_FIELD_OFFSET = 4;
    private static final int LENGTH_ADJUSTMENT = 0;
    private static final int INITIAL_BYTES_TO_STRIP = 0;

    private static final int HEADER_SIZE = 5;
    private byte msgType; // 消息类型
    private int len; // 长度

    public SignDecoder() {
        super(MAX_FRAME_LENGTH,
                LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH,
                LENGTH_ADJUSTMENT, INITIAL_BYTES_TO_STRIP);
    }

    @Override
    protected Msg decode(ChannelHandlerContext ctx, ByteBuf in2) throws Exception {
        ByteBuf in = (ByteBuf) super.decode(ctx, in2);
        if(in == null)
            return null;

        if(in.readableBytes() < HEADER_SIZE){
            return null;
        }
        //读取的方式跟编码是的顺序一致
        msgType = in.readByte();//1.类型 1个字节
        len = in.readInt();//2. 长度 1个字节
        if (in.readableBytes() < len) {
            return null;
        }
        ByteBuf buf = in.readBytes(len);
        byte[] bytes = new byte[buf.readableBytes()];//3. 内容 指定长度len
        buf.readBytes(bytes);
        String body = new String(bytes, "utf-8");
        //转换为msg
        Msg msg = new Msg(new MsgHeader(msgType, len), body);
        return msg;
    }
}
