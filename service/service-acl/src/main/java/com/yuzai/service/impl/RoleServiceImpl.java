package com.yuzai.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.yuzai.dao.AdminRoleDao;
import com.yuzai.dao.BaseDao;
import com.yuzai.dao.RoleDao;
import com.yuzai.entity.Role;
import com.yuzai.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = RoleService.class)
@Transactional
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private AdminRoleDao adminRoleDao;

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Override
    public Map<String, Object> getRoleListByAdminId(Long adminId) {
        List<Role> roleList = roleDao.findAll();
        List<Long> roleIds = adminRoleDao.getRoleIdsByAdminId(adminId);
        List<Role> noAssginRoleList = new ArrayList<>();
        List<Role> assginRoleList = new ArrayList<>();
        for (Role role : roleList) {
            if(roleIds.contains(role.getId())){
                assginRoleList.add(role);
            }else {
                noAssginRoleList.add(role);
            }
        }
        Map<String, Object> roleMap = new HashMap<>();
        roleMap.put("assginRoleList",assginRoleList);
        roleMap.put("noAssginRoleList",noAssginRoleList);
        return roleMap;
    }

    @Override
    public void assignRole(Long adminId, Long[] roleIds) {
        adminRoleDao.deleteRoleIdsByAdminId(adminId);
        for (Long roleId : roleIds) {
            if(roleId != null){
                adminRoleDao.insertAdminIdAndRoleId(adminId,roleId);
            }

        }
    }


    @Override
    protected BaseDao<Role> getEntityDao() {
        return roleDao;
    }
}
