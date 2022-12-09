package com.yuzai.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yuzai.entity.UserInfo;
import com.yuzai.result.Result;
import com.yuzai.result.ResultCodeEnum;
import com.yuzai.service.UserInfoService;
import com.yuzai.util.MD5;
import com.yuzai.vo.LoginVo;
import com.yuzai.vo.RegisterVo;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/userInfo")
public class UserInfoController {

    @Reference
    private UserInfoService userInfoService;

    @RequestMapping("/sendCode/{phone}")
    public Result sendCode(@PathVariable String phone, HttpServletRequest request){
        String code = "8888";
        request.getSession().setAttribute("code",code);
        return Result.ok(code);
    }

    @RequestMapping("/register")
    public Result register(@RequestBody RegisterVo registerVo, HttpSession session){
        String phone = registerVo.getPhone();
        String password = registerVo.getPassword();
        String nickName = registerVo.getNickName();
        String code = registerVo.getCode();
        if(StringUtils.isEmpty(phone) || StringUtils.isEmpty(password) || StringUtils.isEmpty(nickName) || StringUtils.isEmpty(code)){
            return Result.build(null, ResultCodeEnum.PARAM_ERROR);
        }
        UserInfo userInfo = userInfoService.getByPhone(phone);
        if(userInfo != null){
            return Result.build(null,ResultCodeEnum.PHONE_REGISTER_ERROR);
        }
        String currentCode = (String) session.getAttribute("code");
        if(!code.equals(currentCode)){
            return Result.build(null,ResultCodeEnum.CODE_ERROR);
        }
        UserInfo userInfo1 = new UserInfo();
        userInfo1.setPhone(phone);
        userInfo1.setPassword(MD5.encrypt(password));
        userInfo1.setNickName(nickName);
        userInfo1.setStatus(1);
        userInfoService.insert(userInfo1);
        return Result.ok();

    }

    @RequestMapping("/login")
    public Result login(@RequestBody LoginVo loginVo,HttpSession session){
        String phone = loginVo.getPhone();
        String password = loginVo.getPassword();
        if(StringUtils.isEmpty(phone) || StringUtils.isEmpty(password)){
            return Result.build(null,ResultCodeEnum.PARAM_ERROR);
        }
        UserInfo userInfo = userInfoService.getByPhone(phone);
        if(userInfo == null){
            return Result.build(null,ResultCodeEnum.ACCOUNT_ERROR);
        }
        if(!MD5.encrypt(password).equals(userInfo.getPassword())){
            return Result.build(null,ResultCodeEnum.PASSWORD_ERROR);
        }
        //校验是否被禁用
        if(userInfo.getStatus() == 0) {
            return Result.build(null, ResultCodeEnum.ACCOUNT_LOCK_ERROR);
        }
        session.setAttribute("userInfo",userInfo);
        Map<String,Object> map = new HashMap<>();
        map.put("nickName",userInfo.getNickName());
        map.put("phone",phone);
        return Result.ok(map);


    }

    @RequestMapping("/logout")
    public Result logout(HttpSession session){
        session.removeAttribute("userInfo");
        return Result.ok();
    }
}
