package com.yuzai.service;

import com.yuzai.entity.HouseBroker;

import java.util.List;

public interface HouseBrokerService extends BaseService<HouseBroker>{

    //根据houseId查询经纪人信息
    List<HouseBroker> getBrokersByHouseId(Long houseId);
}
