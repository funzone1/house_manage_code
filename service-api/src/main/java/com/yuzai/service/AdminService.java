package com.yuzai.service;

import com.yuzai.entity.Admin;

import java.util.List;

public interface AdminService extends BaseService<Admin>{
    List<Admin> findAll();

    Admin getAdminByUsername(String username);
}
