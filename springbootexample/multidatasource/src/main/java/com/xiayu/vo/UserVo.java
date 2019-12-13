package com.xiayu.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserVo implements Serializable {
    private String id;
    private String name;
    private Integer age;
}
