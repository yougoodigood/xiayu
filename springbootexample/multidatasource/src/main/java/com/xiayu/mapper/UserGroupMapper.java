package com.xiayu.mapper;

import com.xiayu.entity.PermissionEntity;
import com.xiayu.entity.UserGroup;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface UserGroupMapper {

    String columnList = " id,user_group_name ";

    @Insert("insert into t_user_group(" + columnList + ") values(#{id},#{userGroupName})")
    boolean insertUserGroup(UserGroup userGroup);

    @Select("select " + columnList + " from t_user_group where id = #{id}")
    PermissionEntity getUserGroup(Integer id);

}
