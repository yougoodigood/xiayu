package com.xiayu.controller;

import com.xiayu.entity.OrderEntity;
import com.xiayu.service.IOrderService;
import com.xiayu.vo.OrderVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @GetMapping(value = "/getOrderById",name = "依据id获取order")
    public OrderVO getOrderById(@RequestParam("orderId") Integer orderId){
        OrderEntity orderEntity = orderService.getOrder(orderId);
        OrderVO orderVO = new OrderVO();
        if (Objects.nonNull(orderEntity)){
            BeanUtils.copyProperties(orderEntity,orderVO);
        }
        return orderVO;
    }
}
