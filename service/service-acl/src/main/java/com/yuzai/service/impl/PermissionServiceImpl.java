package com.yuzai.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.yuzai.dao.BaseDao;
import com.yuzai.dao.PermissionDao;
import com.yuzai.dao.RolePermissionDao;
import com.yuzai.entity.Permission;
import com.yuzai.helper.PermissionHelper;
import com.yuzai.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = PermissionService.class)
@Transactional
public class PermissionServiceImpl extends BaseServiceImpl<Permission> implements PermissionService {

    @Autowired
    private PermissionDao permissionDao;

    @Autowired
    private RolePermissionDao rolePermissionDao;

    @Override
    protected BaseDao<Permission> getEntityDao() {
        return permissionDao;
    }

    @Override
    public List<Map<String, Object>> getPermissionByRoleId(Long roleId) {
        List<Permission> permissions = permissionDao.findAll();
        List<Long> permissionIds = rolePermissionDao.getPermissionIdsByRoleId(roleId);
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (Permission permission : permissions) {
            Map<String,Object> map = new HashMap<>();
            map.put("id",permission.getId());
            map.put("pId",permission.getParentId());
            map.put("name",permission.getName());
            if(permissionIds.contains(permission.getId())){
                map.put("checked",true);
            }
            mapList.add(map);

        }

        return mapList;
    }

    @Override
    public void assignPermission(Long roleId, Long[] permissionIds) {
        rolePermissionDao.deleteByRoleId(roleId);
        for (Long permissionId : permissionIds) {
            if(permissionId != null){
                rolePermissionDao.insertRoleIdAndPermissionId(roleId,permissionId);
            }
        }
    }

    @Override
    public List<Permission> getPermissionMenuByAdminId(Long id) {
        List<Permission> permissionList = null;
        if(id==1L){
            permissionList = permissionDao.findAll();
        }else {
            permissionList = permissionDao.getPermissionMenuByAdminId(id);
        }
        List<Permission> result = PermissionHelper.bulid(permissionList);
        return result;
    }

    @Override
    public List<Permission> findAllMenu() {
        List<Permission> permissions = permissionDao.findAll();
        if(CollectionUtils.isEmpty(permissions)) return null;
        List<Permission> result = PermissionHelper.bulid(permissions);
        return result;
    }

    @Override
    public List<String> getPermissionCodesByAdminId(Long id) {
        List<String> permissionCodes = null;
        if(id==1){
            permissionCodes = permissionDao.getAllPermissionCodes();
        }else {
            permissionCodes = permissionDao.getPermissionCodesByAdminId(id);
        }
        return permissionCodes;
    }
}
