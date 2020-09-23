package com.yaosq.netty.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description
 * @createtime 2020/8/23 22:16
 */
public class NettyServer {

    public static void main(String[] args) throws InterruptedException {
        //定义两个线程组bossGroup, workerGroup
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)//初始化服务器可连接的队列大小，因为服务器处理客户端是顺序处理的，同一时间只能处理一个客户端
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {

                            System.out.println("==> 每一个客户连接到socketChannel hashCode都是不同的，hashCode="+socketChannel.hashCode());
                            //这里可以将每一个channel管理起来，用作之后指定客户端的业务异步推送
                            socketChannel.pipeline().addLast(new NettyServerHandler());
                        }
                    });
            System.out.println("==> netty server is ready...");
            //绑定端口+同步，启动netty服务
            //ChannelFuture 一个异步结果
            ChannelFuture channelFuture = serverBootstrap.bind(6666).sync();

            //异步模型
            channelFuture.addListener((channelFuture0) -> {
                if(channelFuture0.isSuccess()){
                    System.out.println("==> 端口绑定成功");
                }
            });

            //关闭通道并进行监听
            channelFuture.channel().closeFuture().sync();

        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
