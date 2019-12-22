package com.xiayu.entity;

import lombok.Data;

@Data
public class SystemUserEntity {
    private int id;
    private String userName;
    private String password;
    private int userGroupId;
}
