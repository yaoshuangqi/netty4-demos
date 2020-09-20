package com.yaosq.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description
 * @createtime 2020/9/19 19:50
 */
public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        //向管道增加处理器

        //得到管道
        ChannelPipeline pipeline = socketChannel.pipeline();

        //加入一个netty提供的编解码器
        pipeline.addLast("myHttpServerCodec", new HttpServerCodec());

        //增加一个自定义的处理器
        pipeline.addLast("myHttpServerHandler", new HttpServerHandler());

    }
}
