package com.yaosq.netty.rpc.client;

import com.yaosq.netty.rpc.rpcServerApi.interfaces.HelloService;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description
 * @createtime 2020/9/26 10:01
 */
public class ClientBootStrapTest {

    //这里定义协议头
    public static final String providerName = "HelloService#hello#";

    public static void main(String[] args) throws InterruptedException {
        //创建一个消费者
        NettyClient customer = new NettyClient();
        //创建代理对象
        HelloService service = (HelloService) customer.getBean(HelloService.class, providerName);

        //通过代理对象调用服务提供者的方法
        for(int i =0; i<5; i++){
            String res = service.hello("你好 Dubbo");
            System.out.println("调用的结果，res = " + res);
            Thread.sleep(2000);
        }
    }
}
