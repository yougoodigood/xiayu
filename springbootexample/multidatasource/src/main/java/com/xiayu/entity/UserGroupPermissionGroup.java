package com.xiayu.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserGroupPermissionGroup {
    private int id;
    private int groupId;
    private int permissionGroupId;
}
