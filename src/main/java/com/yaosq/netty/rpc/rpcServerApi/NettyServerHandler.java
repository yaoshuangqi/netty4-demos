package com.yaosq.netty.rpc.rpcServerApi;

import com.yaosq.netty.rpc.rpcServerApi.interfaces.HelloServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description
 * @createtime 2020/9/26 9:37
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //获取客户端发送的消息，并调用服务
        System.out.println("msg=" + msg);
        //客户端在调用服务器的api 时，我们需要定义一个协议
        //比如要求，每次发消息时，都必须以某个字符串开头 "HelloService#hello#你好"
        if (msg.toString().startsWith("HelloService#hello#")) {
            String result = new HelloServiceImpl().hello(msg.toString().substring(msg.toString().lastIndexOf("#") + 1));
            ctx.writeAndFlush(result);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
