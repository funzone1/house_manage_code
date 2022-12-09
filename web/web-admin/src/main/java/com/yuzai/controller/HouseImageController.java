package com.yuzai.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yuzai.entity.HouseImage;
import com.yuzai.result.Result;
import com.yuzai.service.HouseImageService;
import com.yuzai.util.QiniuUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/houseImage")
public class HouseImageController {

    @Reference
    private HouseImageService houseImageService;

    @RequestMapping("/uploadShow/{houseId}/{type}")
    public String toUploadPage(@PathVariable("houseId") Long houseId,@PathVariable("type") Integer type, Map map){

        map.put("houseId",houseId);
        map.put("type",type);

        return "house/upload";
    }

    @RequestMapping("/upload/{houseId}/{type}")
    public Result upload(@PathVariable("houseId") Long houseId, @PathVariable("type") Integer type,
                         @RequestParam("file") MultipartFile[] files){
        try {
            if(files!=null && files.length > 0){
                for (MultipartFile file : files) {
                    byte[] bytes = file.getBytes();
                    String originalFilename = file.getOriginalFilename();
                    String newFileName = UUID.randomUUID().toString();
                    QiniuUtils.upload2Qiniu(bytes,newFileName);
                    HouseImage houseImage = new HouseImage();
                    houseImage.setHouseId(houseId);
                    houseImage.setType(type);
                    houseImage.setImageName(originalFilename);
                    houseImage.setImageUrl("http://rm754vu09.hn-bkt.clouddn.com/"+newFileName);
                    houseImageService.insert(houseImage);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.ok();
    }

    @RequestMapping("/delete/{houseId}/{id}")
    public String delete(@PathVariable("houseId") Long houseId,@PathVariable("id") Long id){
        houseImageService.delete(id);
        return "redirect:/house/"+houseId;
    }
}


