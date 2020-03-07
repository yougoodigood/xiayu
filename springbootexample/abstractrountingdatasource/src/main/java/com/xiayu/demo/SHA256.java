package com.xiayu.demo;


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//public class SHA256 {
//    public static String getSHA256StrJava(String data){
//        MessageDigest messageDigest;
//        String encodeStr = "";
//        try {
//            messageDigest = MessageDigest.getInstance("SHA-256");
//            messageDigest.update(data.getBytes("UTF-8"));
//            encodeStr = byte2Hex(messageDigest.digest());
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        return encodeStr;
//    }
//
//    private static String byte2Hex(byte[] bytes){
//        StringBuffer stringBuffer = new StringBuffer();
//        String temp = null;
//        for (int i=0;i<bytes.length;i++){
//            temp = Integer.toHexString(bytes[i] & 0xFF);
//            if (temp.length()==1){
//                stringBuffer.append("0");
//            }
//            stringBuffer.append(temp);
//        }
//        return stringBuffer.toString();
//    }
//
//    /***
//     * 利用Apache的工具类实现SHA-256加密
//     * @param data 加密后的报文
//     * @return
//     */
//    public static String getSHA256Str(String data){
//        if (data == null || data.length() == 0){
//            return null;
//        }
//        MessageDigest messageDigest;
//        String encdeStr;
//        try {
//            messageDigest = MessageDigest.getInstance("SHA-256");
//            byte[] hash = messageDigest.digest(data.getBytes("UTF-8"));
//            encdeStr = Hex.encodeHexString(hash);
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        return encdeStr;
//    }
//}
