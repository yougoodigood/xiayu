package com.xiayu.controller;

import com.xiayu.entity.SystemUserEntity;
import com.xiayu.service.ISystemUserService;
import com.xiayu.vo.SystemUserVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/systemUser")
public class SystemUserController {
    @Autowired
    private ISystemUserService systemUserService;

//    @RequiresGuest
//    @RequiresUser
//    @RequiresRoles("")
//    @RequiresPermissions("")
//    @RequiresAuthentication
    @PutMapping("/login")
    public String login(@RequestParam("userName") String userName,@RequestParam("password") String password){

        //1.获取Subject
        Subject subject = SecurityUtils.getSubject();

        //2.封装用户数据
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(userName,password);

        //3.执行登录方法
        try {
            subject.login(usernamePasswordToken);
        } catch (UnknownAccountException e) {
            return "未知账户";
        }catch (IncorrectCredentialsException e) {
            return "错误的密码";
        }catch (Exception e){
            return "未知错误";
        }

        if (subject.isAuthenticated()){
            return "登录成功";
        }else {
            return "登录失败";
        }
    }

    @RequiresRoles("administrator")
//    @RequiresPermissions("/systemUser/getSystemUserInfo")
    @GetMapping("/getSystemUserInfo")
    public SystemUserVo getSystemUserInfo(@RequestParam("systemUserName") String systemUserName){
        SystemUserEntity systemUserEntity = systemUserService.getSystemUserByUserName(systemUserName);
        SystemUserVo systemUserVo = new SystemUserVo();
        BeanUtils.copyProperties(systemUserEntity,systemUserVo);
        return systemUserVo;
    }

    @GetMapping("/toLogin")
    public String systemUserToLogin(){
        return "请登录";
    }

    @GetMapping("/noAuth")
    public String systemNoAuth(){
        return "未认证";
    }


}
