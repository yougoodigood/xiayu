package com.xiayu.mapper;

import com.xiayu.entity.AddressEntity;
import com.xiayu.entity.OrderEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface OrderMapper {

    String columnList = " ID,ORDER_DETAIL ";

    @Insert("insert into t_order(" +columnList+") values(#{id},#{orderDetail})")
    boolean insertOrder(OrderEntity orderEntity);

    @Select("select " + columnList + "from t_order where id = #{id}")
    AddressEntity getOrderById(String id);
}
