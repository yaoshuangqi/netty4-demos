package com.yaosq.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description 通过Selector选择器来监听Channel
 *
 * @createtime 2020/8/8 18:52
 */
public class NIOSocketChannel2 {

    public static void main(String[] args) throws IOException {

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        serverSocketChannel.socket().bind(new InetSocketAddress(7001));

        Selector selector = Selector.open();
        //设置非阻塞
        serverSocketChannel.configureBlocking(false);
    }
}
