package com.xiayu.user;


import com.xiayu.message.Message;
import com.xiayu.message.MessageDao;
import org.apache.shiro.util.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Locale;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestDatasource {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private MessageDao messageDao;

    private static String QUERY_MESSAGE_SQL = "select base_name,lan,k,v from t_message where lan = ? and k = ?";

    @Test
    public void TestShardingDatasource() throws Exception{
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(QUERY_MESSAGE_SQL);
        preparedStatement.setString(1,"zh_CN");
        preparedStatement.setString(2,"notice");
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()){
            Message message = Message.builder().basename(rs.getString("base_name"))
                    .language(rs.getString("lan"))
                    .key(rs.getString("k"))
                    .value(rs.getString("v")).build();

        }
        preparedStatement.close();
        connection.close();
//        try (Connection conn = shardingDatasource.getConnection()) {
//            try (PreparedStatement stmt = conn.prepareStatement(QUERY_MESSAGE_SQL)) {
//                stmt.setString(1,"zh_CN");
//                stmt.setString(2,"notice");
//                try (ResultSet rs = stmt.executeQuery()) {
//                    Message.builder().basename(rs.getString("basename"))
//                            .language(rs.getString("language"))
//                            .key(rs.getString("key"))
//                            .value(rs.getString("value")).build();
//                }
//            }
    }

    @Test
    public void testMessageDao(){
        Message notice = messageDao.getMessageByLanguageAndKey(Locale.getDefault().toString(), "notice");
        System.out.println(notice.getValue());
//        Assert.isNull(notice,"true");
    }
}

