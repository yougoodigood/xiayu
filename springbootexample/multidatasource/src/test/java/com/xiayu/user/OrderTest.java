package com.xiayu.user;

import com.xiayu.entity.OrderEntity;
import com.xiayu.mapper.OrderMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderTest {
    @Autowired
    private OrderMapper orderMapper;

    @Test
    public void testInsertOrder(){
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(1);
        orderEntity.setOrderDetail("orderdetail");
        orderMapper.insertOrder(orderEntity);
    }

}
