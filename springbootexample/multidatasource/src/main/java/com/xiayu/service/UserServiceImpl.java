package com.xiayu.service;

import com.xiayu.entity.UserEntity;
import com.xiayu.mapper.UserMapper;
import com.xiayu.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService{

    @Autowired
    private UserMapper userMapper;

    public UserVo getUserByUserId(String userId) {
        UserEntity userEntity = userMapper.getUserById(userId);
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(userEntity,userVo);
        return userVo;
    }

}
