package com.yaosq.netty.codec.server;

import com.yaosq.netty.codec.entity.Msg;
import com.yaosq.netty.codec.entity.MsgType;
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

		Msg msg1 = new Msg();
		msg1.setBody("====> server 已经收到消息了，你放心吧");
		msg1.getMsgHeader().setLen(msg1.getBody().length());
		msg1.getMsgHeader().setMsgType(MsgType.MSG_TYPE_O.getValue());
		ctx.pipeline().writeAndFlush(msg1);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		System.out.println("==>服务器读取消息完成，刷新回复");
	}
}
