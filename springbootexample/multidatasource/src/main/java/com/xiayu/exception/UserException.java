package com.xiayu.exception;

public class UserException extends CommonException{

    public UserException(MessageKey messageKey, String message) {
        super(messageKey, message);
    }

}
