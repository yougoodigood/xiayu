package com.xiayu.mapper;


import com.xiayu.entity.UserEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    String columnList = "NAME,AGE";

    String columnListIncludeId = "ID,NAME,AGE";

    @Select("select " + columnListIncludeId + " from t_user where id = #{userId}")
    UserEntity getUserById(Long userId);

    @Insert("insert into t_user(" +columnList+") values(#{name},#{age})")
    boolean insertUser(UserEntity userEntity);

    @Insert("update t_user set name = #{name},age = #{age} where id = #{id}")
    boolean updateUser(UserEntity userEntity);

}
