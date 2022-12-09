package com.yuzai.dao;

import com.yuzai.entity.HouseImage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HouseImageDao extends BaseDao<HouseImage> {

    List<HouseImage> getImagesByHouseIdAndType(@Param("houseId") Long houseId,@Param("type") Integer type);
}
