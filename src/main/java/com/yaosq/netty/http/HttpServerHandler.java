package com.yaosq.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description HttpObject：表示客户端和服务器端相互的数据封装成的对象
 * @createtime 2020/9/19 19:49
 */
public class HttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    //channelRead0 读取客户端
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttpObject httpObject) throws Exception {
        //判断httpObject是否是HttpRequest
        if(httpObject instanceof HttpRequest){
            /**只要有一个http,就会执行一次*/
            System.out.println("==> msg="+httpObject.getClass());
            System.out.println("==> 客户端的地址："+channelHandlerContext.channel().remoteAddress());

            /**通过如下hashCode，可以得出一个请求就会对应一个handle，pipeline，类似spring中bean的原型*/
            System.out.println("==>pipeline hashCode="+channelHandlerContext.pipeline().hashCode()+", HttpServerHandler hash="+this.hashCode());

            HttpRequest request = (HttpRequest) httpObject;
            System.out.println("==> uri="+request.uri());
            //如果需要进行过滤，直接return即可，无需响应
        }

        //回复  使用池化技术获取一个buffer
        ByteBuf context = Unpooled.copiedBuffer("hello ,我是服务器", CharsetUtil.UTF_8);

        //构造一个http的响应，httpResponse
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, context);

        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, context.readableBytes());

        //给到通道
        channelHandlerContext.writeAndFlush(response);
    }
}
