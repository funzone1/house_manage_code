package com.yuzai.service;

import com.yuzai.entity.Permission;

import java.util.List;
import java.util.Map;

public interface PermissionService extends BaseService<Permission> {
    List<Map<String, Object>> getPermissionByRoleId(Long roleId);

    void assignPermission(Long roleId, Long[] permissionIds);

    List<Permission> getPermissionMenuByAdminId(Long id);

    List<Permission> findAllMenu();

    List<String> getPermissionCodesByAdminId(Long id);
}
