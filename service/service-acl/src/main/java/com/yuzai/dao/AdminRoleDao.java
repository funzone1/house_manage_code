package com.yuzai.dao;

import com.yuzai.entity.AdminRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminRoleDao extends BaseDao<AdminRole> {
    List<Long> getRoleIdsByAdminId(Long adminId);

    void deleteRoleIdsByAdminId(Long adminId);

    void insertAdminIdAndRoleId(@Param("adminId") Long adminId, @Param("roleId") Long roleId);
}
