package com.yuzai.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yuzai.entity.Permission;
import com.yuzai.service.PermissionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;
import java.util.logging.LoggingMXBean;

@Controller
@RequestMapping("/permission")
public class PermissionController {

    @Reference
    private PermissionService permissionService;

    //首页
    @RequestMapping
    public String index(Map map){
        List<Permission> list = permissionService.findAllMenu();
        map.put("list",list);
        return "permission/index";
    }

    //去新增页面
    @RequestMapping("/create")
    public String toAddPage(Permission permission,Map map){
        map.put("permission",permission);
        return "permission/create";
    }

    //新增
    @RequestMapping("/save")
    public String save(Permission permission){
        permissionService.insert(permission);
        return "common/successPage";

    }

    @RequestMapping("/edit/{id}")
    public String toEdit(@PathVariable Long id,Map map){
        Permission permission = permissionService.getById(id);
        map.put("permission",permission);
        return "permission/edit";
    }

    @RequestMapping("/update")
    public String update(Permission permission){
        permissionService.update(permission);
        return "common/successPage";
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        permissionService.delete(id);
        return "redirect:/permission";
    }
}
