package com.xiayu.service.impl;

import com.xiayu.entity.PermissionEntity;
import com.xiayu.mapper.PermissionMapper;
import com.xiayu.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements IPermissionService {
    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<PermissionEntity> getPermissions() {
        List<PermissionEntity> permissiions = permissionMapper.getPermissions();
        return permissiions;
    }
}
