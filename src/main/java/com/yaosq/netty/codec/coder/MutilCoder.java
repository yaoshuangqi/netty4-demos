package com.yaosq.netty.codec.coder;

import io.netty.channel.CombinedChannelDuplexHandler;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description 编解码器
 * @createtime 2020/9/23 21:44
 */
public class MutilCoder extends CombinedChannelDuplexHandler<SignDecoder, SignEncoder> {

    public MutilCoder(){
        super(new SignDecoder(), new SignEncoder());
    }
}
