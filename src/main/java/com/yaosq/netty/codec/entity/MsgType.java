package com.yaosq.netty.codec.entity;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description
 * @createtime 2020/9/23 20:13
 */
public enum MsgType {
    MSG_TYPE_I((byte)0x00),
    MSG_TYPE_O((byte)0x01);

    private byte value;

    MsgType(byte value){
        this.value = value;
    }

    public byte getValue(){
        return value;
    }
}
