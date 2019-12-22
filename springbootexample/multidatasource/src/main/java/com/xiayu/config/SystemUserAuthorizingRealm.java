package com.xiayu.config;

import com.xiayu.entity.SystemUserEntity;
import com.xiayu.service.ISystemUserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
public class SystemUserAuthorizingRealm extends AuthorizingRealm {

    @Autowired
    private ISystemUserService systemUserService;

    @Autowired
    private RedisTemplate redisTemplate;

    //authentication 认证
    //authorization 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取登录用户名
        String userName = (String) principalCollection.getPrimaryPrincipal();
        //查询用户名称
        SystemUserEntity systemUserEntity = systemUserService.getSystemUserByUserName(userName);
//        获取该用户的角色信息
//        systemUserEntity.getRoles();
//        获取权限信息
//        systemUserEntity.getPermissions();

        //添加角色和权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = null;
        if (Objects.nonNull(systemUserEntity)){
            List<String> roles = (List<String>)redisTemplate.opsForValue().get("role:" + systemUserEntity.getUserName());
            if (CollectionUtils.isEmpty(roles)){
                roles = Arrays.asList("guest","dba","administrator");
                redisTemplate.opsForValue().set("role:" + systemUserEntity.getUserName(),roles);
            }
            List<String> permissions = (List<String>) redisTemplate.opsForValue().get("permission:" + systemUserEntity.getUserName());
            if (CollectionUtils.isEmpty(permissions)){
                permissions = Arrays.asList("/systemUser/getSystemUserInfo","/user/updateUserInfo");
                redisTemplate.opsForValue().set("permission:" + systemUserEntity.getUserName(),permissions);
            }
            simpleAuthorizationInfo = new SimpleAuthorizationInfo();
            simpleAuthorizationInfo.addRoles(roles);
            simpleAuthorizationInfo.addStringPermissions(permissions);
        }
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //subject.login(token)
        // 加这一步的目的是在Post请求的时候会先进认证，然后在到请求
        String userName = authenticationToken.getPrincipal().toString();
        if (userName == null) {
            return null;
        }
        //获取用户信息
        SystemUserEntity systemUser = systemUserService.getSystemUserByUserName(userName);
        if (Objects.isNull(systemUser)) {
            return null;
        } else {
            //这里验证authenticationToken和simpleAuthenticationInfo的信息
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(userName, systemUser.getPassword(), getName());
            return simpleAuthenticationInfo;
        }
    }
}
