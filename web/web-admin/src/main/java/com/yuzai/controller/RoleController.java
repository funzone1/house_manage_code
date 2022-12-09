package com.yuzai.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.yuzai.entity.Role;
import com.yuzai.service.PermissionService;
import com.yuzai.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Controller
@RequestMapping("/role")
public class RoleController extends BaseController{

    public static final String SUCCESS_PAGE = "common/successPage";

    @Reference
    private RoleService roleService;

    @Reference
    private PermissionService permissionService;

//    @RequestMapping
//    public String index(Map map){
//        List<Role> roles = roleService.findAll();
//        map.put("list",roles);
//        return "role/index";
//    }
    @RequestMapping
    @PreAuthorize("hasAuthority('role.show')")
    public String index(Map map, HttpServletRequest request){
        Map<String, Object> filters = getFilters(request);
        map.put("filters",filters);
        PageInfo<Role> page = roleService.findPage(filters);
        map.put("page",page);
        return "role/index";

    }

    @PreAuthorize("hasAuthority('role.create')")
    @RequestMapping("/create")
    public String toAddPage(){
        return "role/create";
    }

    @RequestMapping("/save")
    @PreAuthorize("hasAuthority('role.create')")
    public String save(Role role){
        //调用service层函数
        roleService.insert(role);
//        return "redirect:/role";

        return SUCCESS_PAGE;
    }

    @PreAuthorize("hasAuthority('role.delete')")
    @RequestMapping("/delete/{roleId}")
    public String delete(@PathVariable("roleId") Long roleId){
        roleService.delete(roleId);
        return "redirect:/role";
    }

    @PreAuthorize("hasAuthority('role.edit')")
    @RequestMapping("/edit/{roleId}")
    public String toEditPage(@PathVariable("roleId") Long roleId,Map map){
        Role role = roleService.getById(roleId);
        map.put("role",role);
        return "role/edit";
    }

    @PreAuthorize("hasAuthority('role.edit')")
    @RequestMapping("/update")
    public String update(Role role){
        roleService.update(role);
        return SUCCESS_PAGE;

    }

    @PreAuthorize("hasAuthority('role.assign')")
    @RequestMapping("/assignShow/{roleId}")
    public String toAssignShow(@PathVariable Long roleId,Map map){
        map.put("roleId",roleId);
        List<Map<String,Object>> zNodes = permissionService.getPermissionByRoleId(roleId);
        map.put("zNodes",zNodes);
        return "role/assignShow";

    }

    @PreAuthorize("hasAuthority('role.assign')")
    @RequestMapping("/assignPermission")
    public String assignPermission(Long roleId,Long[] permissionIds){
        permissionService.assignPermission(roleId,permissionIds);
        return "common/successPage";
    }


}
