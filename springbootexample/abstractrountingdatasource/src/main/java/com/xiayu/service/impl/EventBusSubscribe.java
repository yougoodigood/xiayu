package com.xiayu.service.impl;

import com.xiayu.eventbus.EventMessage;
import lombok.extern.slf4j.Slf4j;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@Slf4j
public class EventBusSubscribe {
    @Autowired
    private EventBus eventBus;

    @Subscribe
    public void getEventBusMessage(EventMessage eventMessage){
        log.info(eventMessage.getMessage());
        log.info(eventMessage.getType() + " ");
    }

    @PostConstruct
    public void init(){
        eventBus.register(this);
    }
}
