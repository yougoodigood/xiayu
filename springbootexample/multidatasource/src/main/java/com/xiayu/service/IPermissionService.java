package com.xiayu.service;

import com.xiayu.entity.PermissionEntity;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface IPermissionService {

    List<PermissionEntity> getPermissions();
}
