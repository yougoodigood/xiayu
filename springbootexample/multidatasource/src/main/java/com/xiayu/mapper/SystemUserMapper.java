package com.xiayu.mapper;

import com.xiayu.entity.SystemUserEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SystemUserMapper {

    String columList = " id,user_name,password,user_group_id ";

    @Insert("insert into t_system_user(" + columList + ") values(#{id},#{userName},#{password},#{userGroupId})")
    boolean insertSystemUser(SystemUserEntity systemUserEntity);

    @Select("select " + columList + " from t_system_user where id = #{id}")
    SystemUserEntity getSystemUserById(Integer id);

    @Select("select " + columList + " from t_system_user where user_name = #{userName}")
    SystemUserEntity getSystemUserByUserName(String userName);
}
