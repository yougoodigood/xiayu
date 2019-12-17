package com.xiayu.service;

import com.xiayu.entity.UserEntity;
import com.xiayu.mapper.UserMapper;
import com.xiayu.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
//@CacheConfig(cacheNames = "user")
public class UserServiceImpl implements IUserService{

    @Autowired
    private UserMapper userMapper;

//    @Cacheable
    public UserVo getUserByUserId(String userId) {
        UserVo userVo = new UserVo();
        UserEntity userEntity = userMapper.getUserById(userId);
        BeanUtils.copyProperties(userEntity,userVo);
        return userVo;
    }

    public boolean insertUser(UserVo userVo){
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userVo,userEntity);
        return userMapper.insertUser(userEntity);
    }

    public boolean updateUser(UserVo userVo){
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userVo,userEntity);
        return userMapper.updateUser(userEntity);
    }
}
