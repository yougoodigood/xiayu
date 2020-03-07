package com.xiayu.config;

import com.xiayu.eventbus.EventMessage;
import org.greenrobot.eventbus.EventBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class EventBusConf {
    @Bean
    public EventBus eventBus(){
        EventBus eventBus = new EventBus();
        return eventBus;
    }
}
