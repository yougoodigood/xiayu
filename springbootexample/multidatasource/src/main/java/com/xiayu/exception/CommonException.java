package com.xiayu.exception;

import lombok.Data;
import lombok.Setter;

@Data
public class CommonException extends RuntimeException{

    private MessageKey messageKey;

    public CommonException(MessageKey messageKey,String message){
        super(message);
        this.messageKey = messageKey;
    }
}
