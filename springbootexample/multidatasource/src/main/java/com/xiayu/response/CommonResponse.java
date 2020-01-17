package com.xiayu.response;

import lombok.Data;

@Data
public class CommonResponse<T> {

    private Integer code;
    private Boolean success;
    private String message;
    private T data;

    //默认结果
    public CommonResponse(){
        this.code = 200;
        this.success = true;
    }

    //返回数据但没有提示
    public static <T> CommonResponse of(T data) {
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setData(data);
        return commonResponse;
    }

    //返回数据带提示信息
    public static <T> CommonResponse of(T data,String message){
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setData(data);
        commonResponse.setMessage(message);
        return commonResponse;
    }

    //错误信息提示，message为提示信息，code为返回异常状态值
    public static <T> CommonResponse of(String message,Integer code){
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setMessage(message);
        commonResponse.setCode(code);
        commonResponse.setSuccess(false);
        return commonResponse;
    }
}
