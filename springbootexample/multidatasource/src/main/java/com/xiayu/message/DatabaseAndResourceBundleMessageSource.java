package com.xiayu.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;

import javax.sql.DataSource;
import java.util.*;

public class DatabaseAndResourceBundleMessageSource extends ResourceBundleMessageSource {

    private static final Logger log = LoggerFactory.getLogger(DatabaseAndResourceBundleMessageSource.class);

    @Autowired
    DataSource dataSource;

    @Autowired
    private MessageDao messageDao;

    @Override
    protected ResourceBundle doGetBundle(String basename, Locale locale) throws MissingResourceException {
        try {
            ResourceBundle delegate = super.doGetBundle(basename, locale);
            return new ResourceBundle() {
                @Override
                protected Object handleGetObject(String key) {
                    return Optional.<Object>ofNullable(messageDao.getMessageByBaseNameAndLanguageAndKey(basename, locale.getLanguage(), key)).orElseGet(() -> delegate.getObject(key));
                }

                @Override
                public Enumeration<String> getKeys() {
                    Set<String> keys = messageDao.loadKeysFromDb(basename, locale.getLanguage());
                    keys.addAll(delegate.keySet());
                    return Collections.enumeration(keys);
                }
            };
        } catch (MissingResourceException e) {
            return new ResourceBundle() {
                @Override
                protected Object handleGetObject(String key) {
                    return messageDao.getMessageByBaseNameAndLanguageAndKey(basename, locale.getLanguage(), key);
                }

                @Override
                public Enumeration<String> getKeys() {
                    return Collections.enumeration(messageDao.loadKeysFromDb(basename, locale.getLanguage()));
                }
            };
        }
    }

    public Map<String, ?> allMessages(Locale l) {
        Map<String, Object> m = new HashMap<>();
        getBasenameSet().stream().map(baseName->getResourceBundle(baseName, l)).forEach(b->{
            if (b != null) {
                Enumeration<String> keys = b.getKeys();
                while (keys.hasMoreElements()) {
                    String k = keys.nextElement();
                    m.put(k, b.getObject(k));
                }
            }
        });
        return m;
    }
}
