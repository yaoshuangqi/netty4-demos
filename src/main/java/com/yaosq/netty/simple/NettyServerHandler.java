package com.yaosq.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description
 * @createtime 2020/8/23 22:34
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    /**
    * @description 从客户端发送过来的通道读取消息
     * channelHandlerContext：包含一个具体的ChannelHandler,
    * @author quanroong.ysq
    * @createtime 2020/8/23 22:42
    * @version 1.0.0
    */
    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        ByteBuf buf = (ByteBuf)o;
        System.out.println("客户端发送的消息：" + buf.toString(CharsetUtil.UTF_8));
        //比如：有一个非常耗时的业务，需要进行异步执行 ==> 提交channel 对应NioEventLoop 中的taskQueue中

        //解决方案一：用户自定义一个普通任务
        channelHandlerContext.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    //模拟非常耗时的任务
                    Thread.sleep(10*1000);
                    channelHandlerContext.writeAndFlush(Unpooled.copiedBuffer("hello, 我是异步执行任务的", CharsetUtil.UTF_8));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        //可以加多个这样的异步任务，但是要知道，所有的任务都被加入到了NioEventLoop==>channel==>eventLoop==>taskQueue中了，并且是同一个线程
        //如果下一个任务sleep 20*1000，那么这个任务需要等待30s后执行

        //解决方案二：定时任务

        System.out.println("==> go on execute other task.....");

    }
    /**
    * @description 消息读取完成后，做一个消息回发
    * @author quanroong.ysq
    * @createtime 2020/8/23 22:45
    * @version 1.0.0
    */
    @Override
    public void channelReadComplete(ChannelHandlerContext channelHandlerContext) throws Exception {
        channelHandlerContext.writeAndFlush(Unpooled.copiedBuffer("hello , 本次channel读取完成", CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception {
        channelHandlerContext.close();
        throwable.printStackTrace();
    }
}
