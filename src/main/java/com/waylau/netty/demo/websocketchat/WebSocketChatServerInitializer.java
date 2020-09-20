package com.waylau.netty.demo.websocketchat;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * WebSocketChatServer ChannelInitializer.
 * 
 * @since 1.0.0 2020年1月1日
 * @author <a href="https://waylau.com">Way Lau</a>
 */
public class WebSocketChatServerInitializer extends
		ChannelInitializer<SocketChannel> {	//（1）

	@Override
    public void initChannel(SocketChannel ch) throws Exception {//（2）
		 ChannelPipeline pipeline = ch.pipeline();
		 //提供http的解码编码（netty自带）
        pipeline.addLast(new HttpServerCodec());
        //自动整合http数据进行发送（netty自带）
		pipeline.addLast(new HttpObjectAggregator(64*1024));
		//提供大文件传输功能（netty自带）
		pipeline.addLast(new ChunkedWriteHandler());

		pipeline.addLast(new HttpRequestHandler("/ws"));
		//传入ws路径，将Http协议升级成为ws协议 （netty自带）
		pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
		//websocket中进行通信的数据载体处理器，文本帧
		pipeline.addLast(new TextWebSocketFrameHandler());

    }
}
