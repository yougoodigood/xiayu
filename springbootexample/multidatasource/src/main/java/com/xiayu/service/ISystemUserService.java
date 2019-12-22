package com.xiayu.service;

import com.xiayu.entity.SystemUserEntity;
import com.xiayu.vo.SystemUserVo;

public interface ISystemUserService {

    boolean login(String userName,String password);

    boolean loginOut();

    boolean changeUserInfo();

    SystemUserEntity getSystemUserByUserName(String userName);
}
