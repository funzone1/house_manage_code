package com.yuzai.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.yuzai.entity.*;
import com.yuzai.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/house")
public class HouseController extends BaseController{

    @Reference
    private HouseService houseService;

    @Reference
    private CommunityService communityService;

    @Reference
    private DictService dictService;

    @Reference
    private HouseImageService houseImageService;

    @Reference
    private HouseBrokerService houseBrokerService;

    @Reference
    private HouseUserService houseUserService;

    private void getMap(Map map){
        //获取所有小区
        List<Community> communityList = communityService.findAll();
        //获取房屋类型
        List<Dict> houseTypeList = dictService.findListByDictCode("houseType");
        List<Dict> floorList = dictService.findListByDictCode("floor");
        List<Dict> buildStructureList = dictService.findListByDictCode("buildStructure");
        List<Dict> directionList = dictService.findListByDictCode("direction");
        List<Dict> decorationList = dictService.findListByDictCode("decoration");
        List<Dict> houseUseList = dictService.findListByDictCode("houseUse");

        map.put("communityList",communityList);
        map.put("houseTypeList",houseTypeList);
        map.put("floorList",floorList);
        map.put("buildStructureList",buildStructureList);
        map.put("directionList",directionList);
        map.put("decorationList",decorationList);
        map.put("houseUseList",houseUseList);
    }

    @RequestMapping
    public String index(Map map, HttpServletRequest request){
        Map<String, Object> filters = getFilters(request);
        map.put("filters",filters);
        PageInfo<House> pageInfo = houseService.findPage(filters);
        map.put("page",pageInfo);
        getMap(map);

        return "house/index";
    }

    @RequestMapping("/create")
    public String toAddPage(Map map){
        getMap(map);
        return "house/create";
    }

    @RequestMapping("/save")
    public String save(House house){
        houseService.insert(house);
        return "common/successPage";
    }

    @RequestMapping("/edit/{id}")
    public String toEditPage(@PathVariable("id") Long id, Map map){

        House house = houseService.getById(id);
        map.put("house",house);
        getMap(map);
        return "house/edit";
    }

    @RequestMapping("/update")
    public String update(House house){
        houseService.update(house);
        return "common/successPage";
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        houseService.delete(id);
        return "redirect:/house";
    }

    @RequestMapping("/publish/{id}/{status}")
    public String publish(@PathVariable("id") Long id,@PathVariable("status") Integer status){
        houseService.publish(id,status);
        return "redirect:/house";
    }


    //查询房源信息
    @RequestMapping("/{houseId}")
    public String show(@PathVariable Long houseId,Map map){
        House house = houseService.getById(houseId);
        map.put("house",house);
        Community community = communityService.getById(house.getCommunityId());
        map.put("community",community);
        //房源图片
        List<HouseImage> houseImage1List = houseImageService.getImagesByHouseIdAndType(houseId, 1);
        //查询房产图片
        List<HouseImage> houseImage2List = houseImageService.getImagesByHouseIdAndType(houseId, 2);
        //查询经纪人信息
        List<HouseBroker> houseBrokerList = houseBrokerService.getBrokersByHouseId(houseId);
        //查询房东信息
        List<HouseUser> houseUserList = houseUserService.getHouseUserByHouseId(houseId);
        map.put("houseImage1List",houseImage1List);
        map.put("houseImage2List",houseImage2List);
        map.put("houseBrokerList",houseBrokerList);
        map.put("houseUserList",houseUserList);

        return "house/show";
    }
}
