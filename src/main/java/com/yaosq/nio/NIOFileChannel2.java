package com.yaosq.nio;


import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description 测试FileChannel通道,将文件中的数据读出来，并打印到控制台
 * @createtime 2020/8/8 16:26
 */
public class NIOFileChannel2 {

    public static void main(String[] args) {

        try(FileInputStream fileInputStream = new FileInputStream("g:\\file01.txt")){

            FileChannel channel = fileInputStream.getChannel();

            //创建一个缓存区
            ByteBuffer byteBuffer = ByteBuffer.allocate(fileInputStream.available());

            //从通道中读取数据到缓存中
            channel.read(byteBuffer);

            System.out.println("====>"+new String(byteBuffer.array()));
        }catch (IOException e){
            e.printStackTrace();
        }

        //根据通道，缓冲区的双向，可以实现一个复制功能(这个需要注意重置)

        //还可以使用transferFrom()方式进行拷贝

        //transferTo 采用零拷贝

    }
}
