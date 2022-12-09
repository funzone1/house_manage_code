package com.yuzai.service;

import com.yuzai.entity.HouseImage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HouseImageService extends BaseService<HouseImage> {

    List<HouseImage> getImagesByHouseIdAndType(Long houseId, Integer type);
}
