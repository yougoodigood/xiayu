package com.xiayu.user;

import com.xiayu.entity.SystemUserEntity;
import com.xiayu.entity.UserEntity;
import com.xiayu.mapper.UserMapper;
import com.xiayu.service.ISystemUserService;
import lombok.ToString;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
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

    @Autowired
    private ISystemUserService systemUserService;

    @Test
    public void getUserById() throws Exception{
//        primaryDataSource.getConnection();
        UserEntity userEntity = userMapper.getUserById(1L);
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

    @Test
    public void testSalt(){
//        c3VubGlxaWFu
        ByteSource credentialsSalt = ByteSource.Util.bytes("sunliqian");
        SimpleHash simpleHash = new SimpleHash("MD5", "123456", credentialsSalt, 2);
        System.out.println(simpleHash);
        Md5Hash md5Hash = new Md5Hash("123456",credentialsSalt,2);
        System.out.println(md5Hash.toString());
//        f943c03d100392c4d27398ff89dd8a7e
    }

    @Test
    public void testSystemUser(){
        SystemUserEntity systemUser = systemUserService.getSystemUserByUserName("sunliqian");
        System.out.println(systemUser.getUserName());
    }
}
