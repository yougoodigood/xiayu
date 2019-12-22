package com.xiayu.mapper;

import com.xiayu.entity.PermissionEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PermissionMapper {

    String columnList = " id,permission_name,url ";

    @Insert("insert into t_permission(" + columnList + ") values(#{id},#{permissionName},#{url})")
    boolean insertPermission(PermissionEntity permissionEntity);

    @Select("select " + columnList + " from t_permission where id = #{id}")
    PermissionEntity getPermissionById(Integer id);

    @Select("select " + columnList + " from t_permission")
    List<PermissionEntity> getPermissions();

}
