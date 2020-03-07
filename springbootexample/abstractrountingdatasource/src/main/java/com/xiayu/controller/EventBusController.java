package com.xiayu.controller;

import com.xiayu.eventbus.EventMessage;
import org.greenrobot.eventbus.EventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventBusController {

    @Autowired
    private EventBus eventBus;

    @GetMapping("/postMessage")
    public void postMessage(){
        EventMessage eventMessage = new EventMessage(1000,"+8615434789009");
        eventBus.post(eventMessage);
    }
}
