package com.xiayu.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Locale;

@Controller
@ResponseBody
@RequestMapping("/public")
public class MessageController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource(name = "messageSource")
    private MessageSource messageSource;

    @GetMapping("/getMessage")
    public String getMessage(@RequestParam("key") String key){
        Locale locale = Locale.getDefault();
        logger.info("locale:"+locale.getLanguage());
        String message = messageSource.getMessage(key, null, "no such message", locale);
        return message;
    }
}
