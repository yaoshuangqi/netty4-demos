package com.yaosq.netty.codec.server;

import com.yaosq.netty.codec.entity.Msg;
import com.yaosq.netty.codec.entity.MsgHeader;
import com.yaosq.netty.codec.entity.MsgType;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;

/**
 * My ClientHandler.
 * 
 * @since 1.0.0 2019年12月16日
 * @author <a href="https://waylau.com">Way Lau</a>
 */
public class ClientHandler extends SimpleChannelInboundHandler<Msg> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Msg msg) throws Exception {
		Channel incoming = ctx.channel();
		System.out.println("客户端读取的数据：" + incoming.remoteAddress() + msg.getBody());
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ChannelPipeline f = ctx.pipeline();
		//激活客户端时发送消息
//		ctx.channel().eventLoop().execute(new Runnable() {
//			@Override
//			public void run() {
//				while (true){
//					String body = "你好的，我分城的。的饿饿";
//					byte[] bodyBytes = body.getBytes(Charset.forName("utf-8"));
//					int bodySize = bodyBytes.length;
//
//
//					// 发送消息给服务器
//					Msg msg = new Msg();
//					msg.getMsgHeader().setMsgType(MsgType.MSG_TYPE_O.getValue());
//					msg.getMsgHeader().setLen(bodySize);
//					msg.setBody(body);
//
//					f.channel().writeAndFlush(msg);
//					try {
//						Thread.sleep(2000);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				}
//			}
//		});
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		System.out.println("==> 客户端接收消息完成");
	}
}
