package com.xiayu.user;

import com.xiayu.message.Message;
import com.xiayu.message.MessageDao;
import com.xiayu.message.MessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Locale;


@SpringBootTest
@RunWith(SpringRunner.class)
public class TestMessageService {

    @Autowired
    private MessageService messageService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private MessageDao messageDao;

    private Logger logger = LoggerFactory.getLogger(TestMessageService.class);

    @Test
    public void testMessage(){
        Locale local = Locale.getDefault();
        logger.info("local:"+local.toString());
        String notice = messageService.getMessageFromDB("notice", local);
        logger.info("notice:"+notice);
    }

    @Test
    public void testMessageSource(){
//        String notice = messageSource.getMessage("notice", null, Locale.getDefault());
        String message = messageSource.getMessage("notice", null,"default value", Locale.getDefault());
        logger.info("notice:" + message);
    }

    @Test
    public void testGetMessageByBaseNameAndLanguageAndKey(){
        String messageByBaseNameAndLanguageAndKey = messageDao.getMessageByBaseNameAndLanguageAndKey("app/notice", "zh_CN", "notice");
        Message message = messageDao.getMessageByLanguageAndKey("zh_CN", "notice");
        logger.info("messageByBaseNameAndLanguageAndKey:"+messageByBaseNameAndLanguageAndKey);
        logger.info("message:"+message);
    }
}
