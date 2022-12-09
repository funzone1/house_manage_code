package com.yuzai.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.yuzai.entity.UserFollow;
import com.yuzai.entity.UserInfo;
import com.yuzai.result.Result;
import com.yuzai.service.UserFollowService;
import com.yuzai.vo.UserFollowVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/userFollow")
public class UserFollowController {

    @Reference
    private UserFollowService userFollowService;

    @RequestMapping("/auth/follow/{houseId}")
    public Result follow(@PathVariable Long houseId, HttpSession session){
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        Long userId = userInfo.getId();
        userFollowService.follow(houseId,userId);
        return Result.ok();
    }

    @RequestMapping("/auth/list/{pageNum}/{pageSize}")
    public Result findListPage(@PathVariable("pageNum") Integer pageNum,@PathVariable("pageSize") Integer pageSize,HttpSession session){
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        Long userId = userInfo.getId();
        PageInfo<UserFollowVo> pageInfo = userFollowService.findPageList(pageNum,pageSize,userId);
        return Result.ok(pageInfo);

    }

    @RequestMapping("/auth/cancelFollow/{id}")
    public Result cancelFollow(@PathVariable Long id){
        userFollowService.cancelFollow(id);
        return Result.ok();
    }
}
