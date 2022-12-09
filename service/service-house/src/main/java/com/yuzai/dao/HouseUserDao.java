package com.yuzai.dao;

import com.yuzai.entity.HouseUser;

import java.util.List;

public interface HouseUserDao extends BaseDao<HouseUser>{

    List<HouseUser> getHouseUserByHouseId(Long houseId);
}
