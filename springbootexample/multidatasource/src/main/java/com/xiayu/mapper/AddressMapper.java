package com.xiayu.mapper;

import com.xiayu.entity.AddressEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AddressMapper {

    String columnList = " ID,ADDRESS ";

    @Insert("insert into t_address(" +columnList+") values(#{id},#{address})")
    boolean insertAddress(AddressEntity addressEntity);

    @Select("select " + columnList + "from t_address where id = #{id}")
    AddressEntity getAddressById(String id);
}
