package com.yaosq.netty.codec.server;

import com.yaosq.netty.codec.coder.MutilCoder;
import com.yaosq.netty.codec.entity.Msg;
import com.yaosq.netty.codec.entity.MsgType;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.nio.charset.Charset;

/**
 * My Client.
 * 
 * @since 1.0.0 2019年12月16日
 * @author <a href="https://waylau.com">Way Lau</a>
 */
public class NettyClient {

	private String host;
	private int port;

	public NettyClient(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public void run() throws InterruptedException {

		EventLoopGroup workerGroup = new NioEventLoopGroup();

		try {
			Bootstrap b = new Bootstrap();
			b.group(workerGroup);
			b.channel(NioSocketChannel.class);
			b.option(ChannelOption.SO_KEEPALIVE, true);
			b.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				public void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast("multiCoder", new MutilCoder());
					ch.pipeline().addLast(new ClientHandler());
				}
			});

			// 启动客户端
			ChannelFuture f = b.connect(host, port).sync();
			System.out.println("==> client start .....port="+port);
			//f.channel().closeFuture().sync();
			while (true){
				String body = "你好的，我分城的。的饿饿";
				byte[] bodyBytes = body.getBytes(Charset.forName("utf-8"));
				int bodySize = bodyBytes.length;


				// 发送消息给服务器
				Msg msg = new Msg();
				msg.getMsgHeader().setMsgType(MsgType.MSG_TYPE_O.getValue());
				msg.getMsgHeader().setLen(bodySize);
				msg.setBody(body);

				f.channel().writeAndFlush(msg);
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} finally {
			workerGroup.shutdownGracefully();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		new NettyClient("localhost", 8082).run();
	}

}
