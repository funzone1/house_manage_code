package com.yuzai.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.yuzai.dao.AdminDao;
import com.yuzai.dao.BaseDao;
import com.yuzai.entity.Admin;
import com.yuzai.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service(interfaceClass = AdminService.class)
@Transactional
public class AdminServiceImpl extends BaseServiceImpl<Admin> implements AdminService {
    @Autowired
    private AdminDao adminDao;
    @Override
    protected BaseDao<Admin> getEntityDao() {
        return adminDao;
    }

    @Override
    public List<Admin> findAll() {
        return adminDao.findAll();
    }

    @Override
    public Admin getAdminByUsername(String username) {
        return adminDao.getAdminByUsername(username);
    }
}
