package com.yaosq.netty.codec.server;

import com.yaosq.netty.codec.entity.Msg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
* @description 业务handler
* @author quanroong.ysq
* @createtime 2020/9/23 21:46
* @version 1.0.0
*/
public class ServerHandler extends SimpleChannelInboundHandler<Msg> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Msg msg) throws Exception {
		System.out.println("服务器接收的数据：" + ctx.pipeline().channel().remoteAddress() + msg.getBody());
		ctx.pipeline().write(msg);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		System.out.println("==>服务器读取消息完成，刷新回复");
		ctx.flush();
	}
}
