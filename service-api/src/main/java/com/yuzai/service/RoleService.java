package com.yuzai.service;

import com.yuzai.entity.Role;

import java.util.List;
import java.util.Map;

public interface RoleService extends BaseService<Role>{

    List<Role> findAll();


    Map<String, Object> getRoleListByAdminId(Long adminId);

    void assignRole(Long adminId, Long[] roleIds);
}
