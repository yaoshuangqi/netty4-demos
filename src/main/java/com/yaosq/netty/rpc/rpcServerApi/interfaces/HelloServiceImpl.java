package com.yaosq.netty.rpc.rpcServerApi.interfaces;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description
 * @createtime 2020/9/26 9:49
 */
public class HelloServiceImpl implements HelloService {

    @Override
    public String hello(String message) {

        System.out.println("==> message value = " + message);
        return "你好，我是rpc server 实现者";
    }
}
