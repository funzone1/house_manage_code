package com.yuzai.config;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yuzai.entity.Admin;
import com.yuzai.service.AdminService;
import com.yuzai.service.PermissionService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class MyUserDetailService implements UserDetailsService {

    @Reference
    private AdminService adminService;

    @Reference
    private PermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Admin admin = adminService.getAdminByUsername(username);
        if(admin == null){
            throw new UsernameNotFoundException("用户不存在！");
        }
        //用户功能权限
        List<String> codeList = permissionService.getPermissionCodesByAdminId(admin.getId());
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (String code : codeList) {
            if(StringUtils.isEmpty(code)) continue;
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(code);
            grantedAuthorities.add(simpleGrantedAuthority);
        }
        return new User(username,admin.getPassword(),grantedAuthorities);
    }
}
