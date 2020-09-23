package com.yaosq.netty.codec.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description
 * @createtime 2020/9/23 20:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Msg {

    private MsgHeader msgHeader = new MsgHeader();//消息头
    private String body;//消息体，json字符串
}
