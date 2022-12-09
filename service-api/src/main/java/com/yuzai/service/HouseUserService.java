package com.yuzai.service;

import com.yuzai.entity.HouseUser;

import java.util.List;

public interface HouseUserService extends BaseService<HouseUser> {

    List<HouseUser> getHouseUserByHouseId(Long houseId);
}
