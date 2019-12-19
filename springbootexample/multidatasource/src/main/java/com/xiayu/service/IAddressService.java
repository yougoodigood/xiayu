package com.xiayu.service;

import com.xiayu.entity.AddressEntity;
import com.xiayu.vo.AddressVo;

public interface IAddressService {
    boolean insertAddress(AddressVo addressVo);

    AddressVo getAddressById(String id);
}
