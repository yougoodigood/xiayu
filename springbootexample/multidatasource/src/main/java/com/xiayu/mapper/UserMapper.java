package com.xiayu.mapper;


import com.xiayu.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    String columnList = "ID,NAME,AGE";

    @Select("select " + columnList + " from t_user where id = #{userId}")
    UserEntity getUserById(String userId);
}
