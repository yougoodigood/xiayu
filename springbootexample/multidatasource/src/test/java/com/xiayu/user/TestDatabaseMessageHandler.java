package com.xiayu.user;

import com.xiayu.message.DatabaseMessageHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Locale;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestDatabaseMessageHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DatabaseMessageHandler databaseMessageHandler;

    @Test
    public void testMethod(){
        logger.info("Locale.getDefault().toString():"+Locale.getDefault().getLanguage());
        HashSet<String> keys = databaseMessageHandler.loadKeysFromDb("app/notice", Locale.getDefault().getLanguage());
        String notice = databaseMessageHandler.loadMessageFromDb("app/mail", Locale.getDefault().getLanguage(), "mail");
        logger.info("notice",notice);
    }
}
