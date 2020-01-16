package com.xiayu.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public class DatabaseAndResourceBundleMessageSource2 extends ResourceBundleMessageSource {

    private static final Logger log = LoggerFactory.getLogger(DatabaseAndResourceBundleMessageSource2.class);

    @Autowired
    DataSource dataSource;

    @Autowired
    DatabaseMessageHandler databaseMessageHandler;

    @Override
    protected ResourceBundle doGetBundle(String basename, Locale locale) throws MissingResourceException {
        try {
            ResourceBundle delegate = super.doGetBundle(basename, locale);
            return new ResourceBundle() {
                @Override
                protected Object handleGetObject(String key) {
                    log.info("key:"+key);
                    return Optional.<Object>ofNullable(databaseMessageHandler.loadMessageFromDb(basename, locale.getLanguage(), key)).orElseGet(() -> delegate.getObject(key));
                }

                @Override
                public Enumeration<String> getKeys() {
                    log.info("getKeys");
                    Set<String> l = databaseMessageHandler.loadKeysFromDb(basename, locale.getLanguage());
                    l.addAll(delegate.keySet());
                    return Collections.enumeration(l);
                }
            };
        } catch (MissingResourceException e) {
            return new ResourceBundle() {
                @Override
                protected Object handleGetObject(String key) {
                    return databaseMessageHandler.loadMessageFromDb(basename, locale.getLanguage(), key);
                }

                @Override
                public Enumeration<String> getKeys() {
                    return Collections.enumeration(databaseMessageHandler.loadKeysFromDb(basename, locale.getLanguage()));
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

    public void syncToDb(Locale l) throws SQLException {
        String localeLang = Optional.ofNullable(l).map(Locale::getLanguage).orElse("");
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement updateStmt = conn.prepareStatement("UPDATE T_CFG_MESSAGES SET V=? WHERE BASENAME=? AND LOCALE_LANG=? AND K=?")) {
                try (PreparedStatement insertStmt = conn.prepareStatement("INSERT INTO T_CFG_MESSAGES(BASENAME, LOCALE_LANG, K, V) VALUES(?,?,?,?)")) {
                    getBasenameSet().stream().forEach(baseName -> {
                        ResourceBundle b = getResourceBundle(baseName, l);
                        try {
                            if (b != null) {
                                Enumeration<String> keys = b.getKeys();
                                while (keys.hasMoreElements()) {
                                    String k = keys.nextElement();
                                    updateStmt.setString(1, b.getString(k));
                                    updateStmt.setString(2, baseName);
                                    updateStmt.setString(3, localeLang);
                                    updateStmt.setString(4, k);
                                    if (updateStmt.executeUpdate() == 0) {
                                        insertStmt.setString(4, b.getString(k));
                                        insertStmt.setString(1, baseName);
                                        insertStmt.setString(2, localeLang);
                                        insertStmt.setString(3, k);
                                        insertStmt.executeUpdate();
                                    }
                                }
                            }
                        } catch (SQLException e) {
                            log.error(e.getMessage(), e);
                        }
                    });
                }
            }
        }
    }
}
