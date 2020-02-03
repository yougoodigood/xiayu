package com.xiayu.service.impl;

import com.xiayu.annotation.DBRouting;
import com.xiayu.entity.OrderEntity;
import com.xiayu.mapper.OrderMapper;
import com.xiayu.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public boolean insertOrder(OrderEntity orderEntity) {
        return orderMapper.insertOrder(orderEntity);
    }

    @DBRouting(isRead = "true")
    @Override
    public OrderEntity getOrder(Integer orderId) {
        return orderMapper.getOrderById(orderId);
    }
}
