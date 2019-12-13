package com.xiayu.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserEntity implements Serializable {
    private String id;
    private String name;
    private Integer age;
}
