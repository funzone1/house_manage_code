package com.yuzai.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.yuzai.entity.*;
import com.yuzai.result.Result;
import com.yuzai.service.*;
import com.yuzai.vo.HouseQueryVo;
import com.yuzai.vo.HouseVo;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/house")
public class HouseController {

    @Reference
    private HouseService houseService;

    @Reference
    private CommunityService communityService;

    @Reference
    private HouseBrokerService houseBrokerService;

    @Reference
    private HouseImageService houseImageService;

    @Reference
    private UserFollowService userFollowService;

    @RequestMapping("/list/{pageNum}/{pageSize}")
    public Result findListPage(@PathVariable("pageNum") Integer pageNum, @PathVariable("pageSize") Integer pageSize,
                           @RequestBody HouseQueryVo houseQueryVo){
        PageInfo<HouseVo> pageInfo = houseService.findListPage(pageNum,pageSize,houseQueryVo);
        return Result.ok(pageInfo);
    }

    @RequestMapping("/info/{id}")
    public Result info(@PathVariable Long id, HttpSession session){
        House house = houseService.getById(id);
        Community community = communityService.getById(house.getCommunityId());
        List<HouseBroker> houseBrokerList = houseBrokerService.getBrokersByHouseId(id);
        List<HouseImage> houseImage1List = houseImageService.getImagesByHouseIdAndType(id, 1);

        Map map = new HashMap<>();
        map.put("house",house);
        map.put("community",community);
        map.put("houseBrokerList",houseBrokerList);
        map.put("houseImage1List",houseImage1List);
        //关注业务后续补充，当前默认返回未关注
//        map.put("isFollow",false);
        Boolean isFollowed = false;
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        if(userInfo!=null){
           isFollowed = userFollowService.isFollowed(userInfo.getId(),id);
        }
        map.put("isFollow",isFollowed);
        return Result.ok(map);

    }
}
