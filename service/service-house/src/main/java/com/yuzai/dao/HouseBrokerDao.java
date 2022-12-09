package com.yuzai.dao;

import com.yuzai.entity.HouseBroker;

import java.util.List;

public interface HouseBrokerDao extends BaseDao<HouseBroker> {

    //根据houseId查询经纪人信息
    List<HouseBroker> getBrokersByHouseId(Long houseId);
}
