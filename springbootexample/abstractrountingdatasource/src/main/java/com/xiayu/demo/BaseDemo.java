package com.xiayu.demo;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;

public class BaseDemo {
    public static void main(String[] args) throws IOException {
        BASE64Encoder encoder = new BASE64Encoder();
        BASE64Decoder decoder = new BASE64Decoder();
        //此处仅仅为演示demo，这样写违反类单一职责原则
        String data = "zhengzhou";
        String encodeData = encoder.encodeBuffer(data.getBytes());
        byte[] decodeData = decoder.decodeBuffer(encodeData);
        System.out.println(new String(decodeData));
    }
}
