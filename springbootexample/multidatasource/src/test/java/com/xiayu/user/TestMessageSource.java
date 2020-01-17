package com.xiayu.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Locale;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestMessageSource {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource(name = "messageSource")
    private MessageSource messageSource;

    @Test
    public void testMessage(){
        String message = messageSource.getMessage("notice", null, "no such value", Locale.getDefault());
        log.info("message:"+ message);
    }
}
