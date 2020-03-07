package com.xiayu.demo;

import org.springframework.util.DigestUtils;

public class Md5Demo {
    public static void main(String[] args) {
        String s = DigestUtils.md5DigestAsHex("zz".getBytes());
        System.out.println(s);
    }
}
