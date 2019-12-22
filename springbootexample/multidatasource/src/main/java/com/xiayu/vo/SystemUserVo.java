package com.xiayu.vo;

import lombok.Data;

@Data
public class SystemUserVo {
    private int id;
    private String userName;
    private String password;
    private int userGroupId;
}
