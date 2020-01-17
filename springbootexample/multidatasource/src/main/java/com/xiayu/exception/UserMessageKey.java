package com.xiayu.exception;


public enum UserMessageKey implements MessageKey{

    USER_NAME_ERROR("USER_NAME_ERROR",1000),
    USER_PASSWORD_ERROR("USER_PASSWORD_ERROR",1001),
    LOGIN_SUCCESS("LOGIN_SUCESS",1005);

    private Integer code;

    private String key;

    UserMessageKey(String key,Integer code){
        this.code = code;
        this.key = key;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public int getCode() {
        return this.code;
    }
}
