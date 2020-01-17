package com.xiayu.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

@Service
@CacheConfig(cacheNames = "messages")
public class MessageDBHandler {
    @Autowired
    DataSource dataSource;

    private Logger log = LoggerFactory.getLogger(MessageDBHandler.class);

    @Cacheable
    public HashSet<String> loadKeysFromDb(String basename, String language){
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement("SELECT K FROM T_CFG_MESSAGES WHERE BASENAME=? AND LOCALE_LANG=?")) {
                stmt.setString(1, basename);
                stmt.setString(2, language);
                try (ResultSet rs = stmt.executeQuery()) {
                    HashSet<String> results = new HashSet<>();
                    while (rs.next()) {
                        results.add(rs.getString(1));
                    }
                    return results;
                }
            }
        } catch (SQLException e) {
            log.debug(e.getMessage(), e);
            return new HashSet<>();
        }
    }

    @Cacheable
    public String loadMessageFromDb(String basename, String language, String key){
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement("SELECT V FROM T_CFG_MESSAGES WHERE BASENAME=? AND LOCALE_LANG=? AND K=?")) {
                stmt.setString(1, basename);
                stmt.setString(2, language == null ? "": language);
                stmt.setString(3, key);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getString(1);
                    }
                    return null;
                }
            }
        } catch (SQLException e) {
            if(log.isDebugEnabled())
                log.debug(e.getMessage(), e);
            return null;
        }
    }

}
