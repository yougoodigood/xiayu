package com.xiayu.service;

import com.xiayu.vo.UserVo;

public interface IUserService {
    UserVo getUserByUserId(Long userId);

    boolean insertUser(UserVo userVo);

    boolean updateUser(UserVo userVo);
}
