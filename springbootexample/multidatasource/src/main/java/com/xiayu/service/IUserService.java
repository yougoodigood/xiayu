package com.xiayu.service;

import com.xiayu.vo.UserVo;

public interface IUserService {
    UserVo getUserByUserId(String userId);
}
