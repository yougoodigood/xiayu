package com.xiayu.demo;

import org.springframework.util.DigestUtils;

//使用spring的DigestUtils工具
public class Md5Util {

    public static String encodeMd5Hex(String data){
        if (data != null && data.length() > 0){
            String encodedData = DigestUtils.md5DigestAsHex(data.getBytes());
            return encodedData;
        }
        return null;
    }

    public static boolean verify(String data,String encodedData){
        if (data != null && data.length() > 0 && encodedData != null && encodedData.length() > 0){
            return encodedData.equals(DigestUtils.md5Digest(data.getBytes()));
        }
        return false;
    }
}
