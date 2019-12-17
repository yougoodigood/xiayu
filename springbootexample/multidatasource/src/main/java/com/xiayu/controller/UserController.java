package com.xiayu.controller;

import com.xiayu.service.IUserService;
import com.xiayu.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/getUserById")
    public UserVo getUserById(@RequestParam("id") String id){
        UserVo user = userService.getUserByUserId(id);
        return user;
    }

    @PostMapping("/putUser")
    public boolean putUser(@RequestParam("id") String id,@RequestParam("name") String name,@RequestParam("age") Integer age){
        UserVo userVo = UserVo.builder().id(id)
                                        .age(age)
                                        .name(name).build();
        return userService.insertUser(userVo);
    }

    @PostMapping("/updateUser")
    public boolean updateUser(@RequestParam("id") String id,@RequestParam("name") String name,@RequestParam("age") Integer age) {
        UserVo userVo = UserVo.builder().id(id)
                .age(age)
                .name(name).build();
        return userService.updateUser(userVo);
    }
}
