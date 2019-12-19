package com.xiayu.controller;

import com.xiayu.service.IAddressService;
import com.xiayu.vo.AddressVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private IAddressService addressService;

    @PutMapping("/insertAddress")
    public boolean insertAddress(@RequestBody String address){
        AddressVo addressVo = new AddressVo();
        addressVo.setAddress(address);
        return addressService.insertAddress(addressVo);
    }

    @GetMapping("/getAddressById")
    public AddressVo getAddressById(@RequestParam("id") String id){
        AddressVo addressVo = addressService.getAddressById(id);
        return  addressVo;
    }
}
