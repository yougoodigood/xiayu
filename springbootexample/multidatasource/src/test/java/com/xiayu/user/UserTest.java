package com.xiayu.user;

import com.xiayu.entity.UserEntity;
import com.xiayu.mapper.UserMapper;
import lombok.ToString;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {

    @Qualifier("primaryDataSource")
    private DataSource primaryDataSource;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void getUserById() throws Exception{
//        primaryDataSource.getConnection();
        UserEntity userEntity = userMapper.getUserById("1");
        System.out.println(userEntity.getAge());
    }

    @Test
    public void testConnection() throws Exception{
        if (primaryDataSource == null){
            System.out.println("null");
        }
    }

    @Test
    public void testRedisTemplate(){
        redisTemplate.opsForValue().set("sunliqian",267);

    }

    @Test
    public void testGetValue(){
        int sunliqian = (int)redisTemplate.opsForValue().get("sunliqian");
        System.out.println("sunliqian:" + sunliqian);
    }
}
