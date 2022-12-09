package com.yuzai.dao;

import com.yuzai.entity.Permission;

import java.util.List;

public interface PermissionDao extends BaseDao<Permission> {
    List<Permission> findAll();


    List<Permission> getPermissionMenuByAdminId(Long id);

    List<String> getAllPermissionCodes();

    List<String> getPermissionCodesByAdminId(Long id);
}
