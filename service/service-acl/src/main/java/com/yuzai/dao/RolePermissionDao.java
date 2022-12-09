package com.yuzai.dao;

import com.yuzai.entity.RolePermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RolePermissionDao extends BaseDao<RolePermission> {
    List<Long> getPermissionIdsByRoleId(Long roleId);

    void deleteByRoleId(Long roleId);

    void insertRoleIdAndPermissionId(@Param("roleId") Long roleId, @Param("permissionId") Long permissionId);
}
