package com.xiayu.service;

import com.xiayu.entity.OrderEntity;

public interface IOrderService {

    boolean insertOrder(OrderEntity orderEntity);

    OrderEntity getOrder(Integer orderId);
}
