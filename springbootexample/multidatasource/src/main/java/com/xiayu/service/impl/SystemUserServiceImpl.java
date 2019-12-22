package com.xiayu.service.impl;

import com.xiayu.entity.SystemUserEntity;
import com.xiayu.mapper.SystemUserMapper;
import com.xiayu.service.ISystemUserService;
import com.xiayu.vo.SystemUserVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class SystemUserServiceImpl implements ISystemUserService {

    @Autowired
    private SystemUserMapper systemUserMapper;

    @Override
    public boolean login(String userName,String password) {
        SystemUserEntity systemUserEntity = systemUserMapper.getSystemUserByUserName(userName);
        if (Objects.isNull(systemUserEntity)){
            return false;
        }
        String userPassword = systemUserEntity.getPassword();
        if (userPassword.equals(password)){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean loginOut() {
        return false;
    }

    @Override
    public boolean changeUserInfo() {
        return false;
    }

    @Override
    public SystemUserEntity getSystemUserByUserName(String userName){
        return systemUserMapper.getSystemUserByUserName(userName);
    }
}
