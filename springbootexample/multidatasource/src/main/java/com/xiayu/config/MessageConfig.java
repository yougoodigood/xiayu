package com.xiayu.config;

import com.xiayu.message.DatabaseAndResourceBundleMessageSource2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class MessageConfig {

    @Value("message.basename:app/notice,app/mail")
    private String[] baseNames;


    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Bean("messageSource")
    public MessageSource myMessageSource(){
        ResourceBundleMessageSource messageSource = new DatabaseAndResourceBundleMessageSource2();
        messageSource.setBasenames(baseNames);
        return messageSource;
    }
}
