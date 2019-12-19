package com.xiayu.service;

import com.xiayu.entity.AddressEntity;
import com.xiayu.mapper.AddressMapper;
import com.xiayu.vo.AddressVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AddressServiceImpl implements IAddressService{

    @Autowired
    private AddressMapper addressMapper;

    @Override
    public boolean insertAddress(AddressVo addressVo) {
        AddressEntity addressEntity = new AddressEntity();
        addressVo.setId(UUID.randomUUID().toString());
        BeanUtils.copyProperties(addressVo,addressEntity);
        return addressMapper.insertAddress(addressEntity);
    }

    @Override
    public AddressVo getAddressById(String id) {
        AddressEntity addressEntity = addressMapper.getAddressById(id);
        AddressVo addressVo = new AddressVo();
        BeanUtils.copyProperties(addressEntity,addressEntity);
        return addressVo;
    }
}
