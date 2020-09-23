package com.yaosq.netty.codec.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description 根据给定的协议，设置消息头部
 * @createtime 2020/9/23 20:02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MsgHeader {

    private byte msgType;//消息类型
    private int len;//长度
}
