package com.xiayu.controller;

import com.xiayu.exception.UserMessageKey;
import com.xiayu.response.CommonResponse;
import com.xiayu.service.IAddressService;
import com.xiayu.vo.AddressVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.Objects;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private IAddressService addressService;

    @Autowired
    private MessageSource messageSource;

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @PutMapping("/insertAddress")
    public boolean insertAddress(@RequestParam("address") String address){
        AddressVo addressVo = new AddressVo();
        addressVo.setAddress(address);
        return addressService.insertAddress(addressVo);
    }

    @GetMapping("/getAddressById")
    public CommonResponse getAddressById(@RequestParam("id") String id){
        AddressVo addressVo = addressService.getAddressById(id);
        if (Objects.isNull(addressVo)){
            return CommonResponse.of(messageSource.getMessage(UserMessageKey.USER_NAME_ERROR.getKey(),null,UserMessageKey.USER_NAME_ERROR.getKey(), Locale.getDefault()),UserMessageKey.USER_NAME_ERROR.getCode());
        }
        return  CommonResponse.of(addressVo);
    }
}
