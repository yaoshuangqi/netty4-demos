package com.yaosq.netty.rpc.rpcServerApi;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description
 * @createtime 2020/9/26 9:25
 */
public class NettyServer {

    private void run(){
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup(1);

        ServerBootstrap serverBootstrap = new ServerBootstrap();

        try {
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 10)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new StringDecoder())
                                    .addLast(new StringEncoder())
                                    .addLast(new NettyServerHandler());
                        }
                    });

            ChannelFuture channelFuture = serverBootstrap.bind(4444).sync();
            System.out.println("==> 服务已启动，port=4444");
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new NettyServer().run();
    }
}
