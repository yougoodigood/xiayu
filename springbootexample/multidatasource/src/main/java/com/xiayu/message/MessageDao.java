package com.xiayu.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@CacheConfig(cacheNames = "messages")
public class MessageDao {

    private static final Logger log = LoggerFactory.getLogger(MessageDao.class);

    @Autowired
    DataSource dataSource;

    private static String QUERY_SQL = "select basename,lan,k,v from t_message";

    private static String QUERY_MESSAGE_SQL = "select base_name,lan,k,v from t_message where lan = ? and k = ?";

    private static String QUERY_MESSAGE_VALUE_SQL = "select v from t_message where lan = ? and k = ? and base_name = ?";

    private static String QUERY_MESSAGE_KEYS_SQL = "select k from t_message where lan = ? and base_name = ?";

    @Cacheable
    public List<Message> loadMessages(){
        List<Message> list = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(QUERY_SQL)) {
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        list.add(Message.builder().basename(rs.getString("base_name")).language(rs.getString("lan")).key(rs.getString("k")).value(rs.getString("v")).build());
                    }
                }
            }
        } catch (SQLException e) {
            log.debug(e.getMessage(), e);
        }finally {
        }
        return list;
    }

    @Cacheable
    public Message getMessageByLanguageAndKey(String language,String key){
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(QUERY_MESSAGE_SQL)) {
                stmt.setString(1,language);
                stmt.setString(2,key);
                try(ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()){
                        String baseName = rs.getString("base_name");
                        String lan = rs.getString("lan");
                        String k = rs.getString("k");
                        String v = rs.getString("v");
                        return Message.builder().basename(baseName)
                                .language(lan)
                                .key(k)
                                .value(v)
                                .build();
                    }else {
                        return null;
                    }
                }catch (SQLException e){
                    log.debug(e.getMessage(),e);
                    return null;
                }
            }
        } catch (SQLException e) {
            log.debug(e.getMessage(), e);
            return null;
        }
    }

    @Cacheable
    public String getMessageByBaseNameAndLanguageAndKey(String baseName,String language,String key){
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(QUERY_MESSAGE_VALUE_SQL)) {
                stmt.setString(1,language);
                stmt.setString(2,key);
                stmt.setString(3,baseName);
                try(ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()){
                        return rs.getString("v");
                    }else {
                        return null;
                    }
                }catch (SQLException e){
                    log.debug(e.getMessage(),e);
                    return null;
                }
            }
        } catch (SQLException e) {
            log.debug(e.getMessage(), e);
            return null;
        }
    }

    @Cacheable
    public Set<String> loadKeysFromDb(String baseName, String language){
        Set<String> keys = new HashSet<>();
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(QUERY_MESSAGE_KEYS_SQL)) {
                stmt.setString(1,language);
                stmt.setString(2,baseName);
                try(ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()){
                        keys.add(rs.getString("k"));
                    }
                    return keys;
                }catch (SQLException e){
                    log.debug(e.getMessage(),e);
                    return keys;
                }
            }
        } catch (SQLException e) {
            log.debug(e.getMessage(), e);
            return keys;
        }
    }
}

