package com.yaosq.nio;


import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description 测试FileChannel通道,向文件写入数据
 * @createtime 2020/8/8 16:26
 */
public class NIOFileChannel1 {

    public static void main(String[] args) throws IOException {
        String str = "测试向文件写入数据，hello，，，";

        FileOutputStream fileOutputStream = new FileOutputStream("g:\\file01.txt");

        //从原始的BIO流中获取一个channel通道
        FileChannel channel = fileOutputStream.getChannel();

        //创建一个字节缓存区  这个缓存区是双向的，跟通道一样，既可以读也可以写
        ByteBuffer allocate = ByteBuffer.allocate(str.getBytes().length);
        //将数据写入到缓存区
        ByteBuffer put = allocate.put(str.getBytes());

        //反转缓冲区，由写缓存到读缓存
        put.flip();

        //然后向通道中写数据
        channel.write(put);

        fileOutputStream.close();
    }
}
