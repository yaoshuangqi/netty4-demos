package com.yaosq.netty.codec.server;

import com.yaosq.netty.codec.entity.Msg;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
* @description 客户端业务处理器
* @author quanroong.ysq
* @createtime 2020/9/25 21:14
* @version 1.0.0
*/
public class ClientHandler extends SimpleChannelInboundHandler<Msg> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Msg msg) throws Exception {
		Channel incoming = ctx.channel();
		System.out.println("客户端读取的数据：" + incoming.remoteAddress() + msg.getBody());
	}


	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		System.out.println("==> 客户端接收消息完成");
	}
}
