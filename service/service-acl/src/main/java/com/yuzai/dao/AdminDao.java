package com.yuzai.dao;

import com.yuzai.entity.Admin;

import java.util.List;

public interface AdminDao extends BaseDao<Admin>{
    List<Admin> findAll();

    Admin getAdminByUsername(String username);
}
