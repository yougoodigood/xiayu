package com.xiayu.controller;

import com.xiayu.service.IUserService;
import com.xiayu.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/getUserById")
    public UserVo getUserById(){
        UserVo user = userService.getUserByUserId("1");
        return user;
    }
}
