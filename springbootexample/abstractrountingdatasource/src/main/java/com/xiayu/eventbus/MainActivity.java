package com.xiayu.eventbus;


import org.greenrobot.eventbus.EventBus;

public class MainActivity {

    EventBus eventBus = new EventBus();
    public void registry(){
        eventBus.register(this);
    }
}
