package com.xiayu.mapper;

import com.xiayu.entity.SystemUserEntity;
import org.apache.ibatis.annotations.*;

@Mapper
public interface SystemUserMapper {

    String columList = " id,user_name,password,user_group_id ";

    @Insert("insert into t_system_user(" + columList + ") values(#{id},#{userName},#{password},#{userGroupId})")
    boolean insertSystemUser(SystemUserEntity systemUserEntity);


    @Results(id = "systemUserResultMap",value = {
            @Result(property = "id",column = "id"),
            @Result(property = "userName",column = "user_name"),
            @Result(property = "password",column = "password"),
            @Result(property = "userGroupId",column = "user_group_id"),
    })
    @Select("select " + columList + " from t_system_user where id = #{id}")
    SystemUserEntity getSystemUserById(Integer id);

    @ResultMap("systemUserResultMap")
    @Select("select " + columList + " from t_system_user where user_name = #{userName}")
    SystemUserEntity getSystemUserByUserName(String userName);
}
