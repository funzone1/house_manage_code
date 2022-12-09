package com.yuzai.dao;

import com.yuzai.entity.Role;

import java.util.List;

public interface RoleDao extends BaseDao<Role>{

    public List<Role> findAll();


}
