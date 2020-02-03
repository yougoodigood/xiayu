package com.xiayu.mapper;

import com.xiayu.entity.OrderEntity;
import org.apache.ibatis.annotations.*;

@Mapper
public interface OrderMapper {

    String COLUMN_LIST = " id,order_name,order_type ";

    @Insert("insert into t_order( " + COLUMN_LIST + ") values(#{id},#{orderName},#{orderType})")
    boolean insertOrder(OrderEntity orderEntity);

    @Results(id = "orderResultMap",value = {
            @Result(property = "id",column = "id"),
            @Result(property = "orderName",column = "order_name"),
            @Result(property = "orderType",column = "order_type"),
    })
    @Select("select " + COLUMN_LIST + " from t_order where id = #{id}")
    OrderEntity getOrderById(Integer id);

}