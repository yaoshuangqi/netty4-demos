package com.yaosq.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description
 * @createtime 2020/8/23 22:34
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    /**
    * @description 从客户端发送过来的通道读取消息
    * @author quanroong.ysq
    * @createtime 2020/8/23 22:42
    * @version 1.0.0
    */
    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        ByteBuf buf = (ByteBuf)o;
        System.out.println("客户端发送的消息：" + buf.toString(CharsetUtil.UTF_8));
    }
    /**
    * @description 消息读取完成后，做一个消息回发
    * @author quanroong.ysq
    * @createtime 2020/8/23 22:45
    * @version 1.0.0
    */
    @Override
    public void channelReadComplete(ChannelHandlerContext channelHandlerContext) throws Exception {
        channelHandlerContext.writeAndFlush(Unpooled.copiedBuffer("hello , 客户端。你好", CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception {
        channelHandlerContext.close();
        throwable.printStackTrace();
    }
}
