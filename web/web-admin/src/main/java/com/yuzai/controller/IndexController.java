package com.yuzai.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yuzai.entity.Admin;
import com.yuzai.entity.Permission;
import com.yuzai.service.AdminService;
import com.yuzai.service.PermissionService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
public class IndexController {

    @Reference
    private AdminService adminService;

    @Reference
    private PermissionService permissionService;

    @RequestMapping("/")
    public String index(Map map){
//        Long userId = 1L;
//        Admin admin = adminService.getById(userId);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Admin admin = adminService.getAdminByUsername(user.getUsername());
        List<Permission> permissionList = permissionService.getPermissionMenuByAdminId(admin.getId());
        map.put("admin",admin);
        map.put("permissionList",permissionList);
        return "frame/index";
    }

    @RequestMapping("/main")
    public String main(){
        return "frame/main";
    }

    @RequestMapping("/login")
    public String login(){
        return "frame/login";
    }

    //去没有权限页面
    @RequestMapping("/auth")
    public String auth(){
        return "frame/auth";
    }
}
