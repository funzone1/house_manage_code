package com.yuzai.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yuzai.entity.Admin;
import com.yuzai.entity.HouseBroker;
import com.yuzai.service.AdminService;
import com.yuzai.service.HouseBrokerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/houseBroker")
public class HouseBrokerController {

    @Reference
    private AdminService adminService;

    @Reference
    private HouseBrokerService houseBrokerService;

    @RequestMapping("/create")
    public String toAddPage(@RequestParam("houseId") Long houseId, Map map){
        map.put("houseId",houseId);
        List<Admin> adminList = adminService.findAll();
        map.put("adminList",adminList);
        return "houseBroker/create";
    }

    //添加经纪人
    @RequestMapping("/save")
    public String save(HouseBroker houseBroker){
        //根据BrokerId获取经纪人姓名和头像
        Admin admin = adminService.getById(houseBroker.getBrokerId());
        houseBroker.setBrokerName(admin.getName());
        houseBroker.setBrokerHeadUrl(admin.getHeadUrl());
        houseBrokerService.insert(houseBroker);
        return "common/successPage";
    }

    @RequestMapping("/edit/{brokerId}")
    public String toEditPage(@PathVariable Long brokerId,Map map){

        HouseBroker houseBroker = houseBrokerService.getById(brokerId);
        map.put("houseBroker",houseBroker);
        List<Admin> adminList = adminService.findAll();
        map.put("adminList",adminList);
        return "houseBroker/edit";
    }

//    修改经纪人
    @RequestMapping("/update")
    public String update(HouseBroker houseBroker){
        //根据BrokerId获取经纪人姓名和头像
        Admin admin = adminService.getById(houseBroker.getBrokerId());
        houseBroker.setBrokerName(admin.getName());
        houseBroker.setBrokerHeadUrl(admin.getHeadUrl());
        houseBrokerService.update(houseBroker);
        return "common/successPage";
    }

    //删除经纪人
    @RequestMapping("/delete/{houseId}/{brokerId}")
    public String delete(@PathVariable("houseId") Long houseId,@PathVariable("brokerId") Long brokerId){
        houseBrokerService.delete(brokerId);
        return "redirect:/house/" + houseId;
    }
}
