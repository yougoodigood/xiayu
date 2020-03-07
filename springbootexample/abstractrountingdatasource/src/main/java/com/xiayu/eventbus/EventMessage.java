package com.xiayu.eventbus;

import lombok.Data;

@Data
public class EventMessage {
    private int type;
    private String message;

    public EventMessage(int type,String message){
        this.type = type;
        this.message = message;
    }
}
