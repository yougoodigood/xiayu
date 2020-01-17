package com.xiayu.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class AddressEntity implements Serializable {
    private String id;
    private String address;
}
