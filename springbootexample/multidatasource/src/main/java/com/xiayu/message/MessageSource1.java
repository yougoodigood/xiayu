package com.xiayu.message;

import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.core.io.ResourceLoader;

import java.text.MessageFormat;
import java.util.Locale;

public class MessageSource1 extends AbstractMessageSource implements ResourceLoaderAware {
    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {

    }

    @Override
    protected MessageFormat resolveCode(String s, Locale locale) {
        return null;
    }
}
