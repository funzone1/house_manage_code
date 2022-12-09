package com.yuzai.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.yuzai.entity.Admin;
import com.yuzai.service.AdminService;
import com.yuzai.service.RoleService;
import com.yuzai.util.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController{

    @Reference
    private AdminService adminService;

    @Reference
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PreAuthorize("hasAuthority('admin.show')")
    @RequestMapping
    public String index(Map map, HttpServletRequest request){
        Map<String, Object> filters = getFilters(request);
        map.put("filters",filters);
        PageInfo<Admin> pageInfo = adminService.findPage(filters);
        map.put("page",pageInfo);
        return "admin/index";

    }

    @PreAuthorize("hasAuthority('admin.create')")
    @RequestMapping("/create")
    public String toCreatePage(){
        return "admin/create";
    }

    @PreAuthorize("hasAuthority('admin.create')")
    @RequestMapping("/save")
    public String save(Admin admin){
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        adminService.insert(admin);
        return "common/successPage";
    }

    @PreAuthorize("hasAuthority('admin.delete')")
    @RequestMapping("/delete/{adminId}")
    public String delete(@PathVariable("adminId") Long adminId){
        adminService.delete(adminId);
        return "redirect:/admin";
    }

    @PreAuthorize("hasAuthority('admin.edit')")
    @RequestMapping("/edit/{adminId}")
    public String toEdit(@PathVariable("adminId") Long adminId,Map map){
        Admin admin = adminService.getById(adminId);
        map.put("admin",admin);
        return "admin/edit";
    }

    @PreAuthorize("hasAuthority('admin.edit')")
    @RequestMapping("/update")
    public String update(Admin admin){
        adminService.update(admin);
        return "common/successPage";
    }
    //去上传头像页面
    @RequestMapping("/uploadShow/{id}")
    public String toUpload(@PathVariable Long id,Map map){
        map.put("id",id);
        return "admin/upload";

    }

    @RequestMapping("/upload/{id}")
    public String upload(@PathVariable Long id, MultipartFile file){
        Admin admin = adminService.getById(id);
        try {
            byte[] bytes = file.getBytes();
            String fileName = UUID.randomUUID().toString();
            QiniuUtils.upload2Qiniu(bytes,fileName);
            admin.setHeadUrl("http://rm754vu09.hn-bkt.clouddn.com/"+fileName);
            adminService.update(admin);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "common/successPage";
    }

    @PreAuthorize("hasAuthority('admin.assign')")
    @RequestMapping("/assignShow/{adminId}")
    public String toAssignShow(@PathVariable Long adminId, ModelMap modelMap){
        modelMap.addAttribute("adminId",adminId);
        Map<String,Object> roleMap = roleService.getRoleListByAdminId(adminId);
        modelMap.addAllAttributes(roleMap);
        return "admin/assignShow";
    }

    @PreAuthorize("hasAuthority('admin.assign')")
    @RequestMapping("/assignRole")
    public String assignRole(Long adminId,Long[] roleIds){
        roleService.assignRole(adminId,roleIds);
        return "common/successPage";
    }
}
