package com.yaosq.bio.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description 通过Buffer数组来完成读写操作，即Scattering和Gathering
 * * Scattering：将数据写入到buffer时，可以采用buffer数组，初次写入 【分散】
 * * Gathering：从buffer读取数据时，也可以采用buffer数组，依次读
 *
 * @createtime 2020/8/8 18:52
 */
public class NIOSocketChannel1 {

    public static void main(String[] args) throws IOException {
        //使用 ServerSocketChannel 和 SocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);
        //serverSocketChannel.configureBlocking(false);//非阻塞
        //绑定端口到socket，并启动
        serverSocketChannel.socket().bind(inetSocketAddress);
        //创建一个Buffer数组
        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);

        //等待客户端的连接（Telnet）
        SocketChannel socketChannel = serverSocketChannel.accept();
        int msgLength = 8; //假定从客户端接受8个字节
        //循环的读取
        while (true) {
            int byteRead = 0;
            while (byteRead < msgLength) {
                long l = socketChannel.read(byteBuffers);
                byteRead += l; //累计读取的字节数
                System.out.println("byteRead= " + byteRead);
                //使用流打印，看看当前这个buffer的position和limit
                Arrays.stream(byteBuffers)
                        .map(buffer -> "position=" + buffer.position() + ", limit = " + buffer.limit())
                        .forEach(System.out::println);
            }
            //读书数据后需要将所有的buffer进行flip
            Arrays.asList(byteBuffers).forEach(Buffer::flip);

            //将数据读出显示到客户端
            long byteWrite = 0;
            while (byteWrite < msgLength) {
                long l = socketChannel.write(byteBuffers);
                byteWrite += l;
            }

            //将所有的 buffer 进行clear操作
            Arrays.asList(byteBuffers).forEach(Buffer::clear);
            System.out.println("byteRead=" + byteRead + ", byteWrite=" + byteWrite
                    + ", msgLength=" + msgLength);
        }
    }
}
