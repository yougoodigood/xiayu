package com.xiayu.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class MessageDao {

    private static final Logger log = LoggerFactory.getLogger(MessageDao.class);

    @Autowired
    DataSource dataSource;

    private static String QUERY_SQL = "select basename,locale_lang as localLang,k,v from T_CFG_MESSAGES where basename=?";

    private static String QUERY_By_KEy_SQL = "select basename,locale_lang as localLang,k,v from T_CFG_MESSAGES where basename=? and k =?";

    private static String UPDATE_SQL = "update T_CFG_MESSAGES set v=? where basename=? and locale_lang=? and k=?";

    private static String CREATE_SQL = "insert into T_CFG_MESSAGES (basename,locale_lang,k,v) values(?,?,?,?)";

    private static String CHECK_EXIST = "select basename,locale_lang as localLang,k,v from T_CFG_MESSAGES where basename=? and locale_lang=? and k=?";


    public List<Message> loadMessageByBasename(String basename){
        List<Message> list = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(QUERY_SQL)) {
                stmt.setString(1, basename);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        list.add(Message.builder().basename(rs.getString("basename")).localeLang(rs.getString("localLang")).k(rs.getString("k")).v(rs.getString("v")).build());
                    }
                }
            }
        } catch (SQLException e) {
            log.debug(e.getMessage(), e);
        }finally {
        }
        return list;
    }

    public List<Message> loadMessageByBasenameAndKey(String basename,String key){
        List<Message> list = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(QUERY_By_KEy_SQL)) {
                stmt.setString(1, basename);
                stmt.setString(2, key);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        list.add(Message.builder().basename(rs.getString("basename")).localeLang(rs.getString("localLang")).k(rs.getString("k")).v(rs.getString("v")).build());
                    }
                }
            }
        } catch (SQLException e) {
            log.debug(e.getMessage(), e);
        }
        return list;
    }

    public boolean checkExist(Message message){
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(CHECK_EXIST)) {
                stmt.setString(1, message.getBasename());
                stmt.setString(2, message.getLocaleLang());
                stmt.setString(3, message.getK());
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            log.debug(e.getMessage(), e);
        }
        return false;
    }

    public int updateMessage(Message message){
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(UPDATE_SQL)) {
                stmt.setString(1, message.getV());
                stmt.setString(2, message.getBasename());
                stmt.setString(3, message.getLocaleLang());
                stmt.setString(4, message.getK());
                return stmt.executeUpdate();
            }
        } catch (SQLException e) {
            log.debug(e.getMessage(), e);
        }
        return -1;
    }

    public int create(Message message){
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(CREATE_SQL)) {
                stmt.setString(1, message.getBasename());
                stmt.setString(2, message.getLocaleLang());
                stmt.setString(3, message.getK());
                stmt.setString(4, message.getV());
                return stmt.executeUpdate();
            }
        } catch (SQLException e) {
            log.debug(e.getMessage(), e);
        }
        return -1;
    }

    public List<Message> getAll() {
        try (Connection conn = dataSource.getConnection()) {
            try (Statement stmt = conn.createStatement()) {
                List<Message> l = new ArrayList<>();
                try (ResultSet rs = stmt.executeQuery("SELECT * from T_CFG_MESSAGES")) {
                    while (rs.next()) {
                        l.add(Message.builder().basename(rs.getString("basename")).localeLang(rs.getString("locale_lang")).k(rs.getString("k")).v(rs.getString("v")).build());
                    }
                }
                return l;
            }
        } catch (SQLException e) {
            log.debug(e.getMessage(), e);
            return new ArrayList<>();
        }
    }
}

