package com.xiayu.config;

import com.xiayu.message.DatabaseAndResourceBundleMessageSource;
import com.xiayu.message.DatabaseAndResourceBundleMessageSource2;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class MessageConfig {

    @Bean("myMessageSource")
    public ResourceBundleMessageSource myMessageSource(){
        ResourceBundleMessageSource messageSource = new DatabaseAndResourceBundleMessageSource2();
        String[] baseNames = new String[2];
        baseNames[0] = "app/notice";
        baseNames[1] = "app/mail";
        messageSource.setBasenames(baseNames);
        return messageSource;
    }
}
