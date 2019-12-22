package com.xiayu.entity;

import lombok.Data;
import org.apache.shiro.crypto.hash.SimpleHash;

@Data
public class SystemUserEntity {
    private int id;
    private String userName;
//    SimpleHash simpleHash = new SimpleHash("MD5", password, userName, 2);
    private String password;
    private int userGroupId;
}
