package com.yuzai.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yuzai.entity.HouseUser;
import com.yuzai.service.HouseUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/houseUser")
public class HouseUserController {

    @Reference
    private HouseUserService houseUserService;


    @RequestMapping("/create")
    public String toAddPage(@RequestParam("houseId") Long houseId, Map map){
        map.put("houseId",houseId);
        return "houseUser/create";
    }

    @RequestMapping("/save")
    public String save(HouseUser houseUser){
        houseUserService.insert(houseUser);
        return "common/successPage";
    }

    @RequestMapping("/edit/{id}")
    public String toEditPage(@PathVariable Long id,Map map){
        HouseUser houseUser = houseUserService.getById(id);
        map.put("houseUser",houseUser);
        return "houseUser/edit";
    }

    @RequestMapping("/update")
    public String update(HouseUser houseUser){
        houseUserService.update(houseUser);
        return "common/successPage";
    }

    @RequestMapping("/delete/{houseId}/{userId}")
    public String delete(@PathVariable("houseId") Long houseId,@PathVariable("userId") Long userId){
        houseUserService.delete(userId);
        return "redirect:/house/" + houseId;
    }
}
