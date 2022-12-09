package com.yuzai.service;

import com.github.pagehelper.PageInfo;
import com.yuzai.entity.House;
import com.yuzai.vo.HouseQueryVo;
import com.yuzai.vo.HouseVo;

public interface HouseService extends BaseService<House>{
    void publish(Long id, Integer status);

    PageInfo<HouseVo> findListPage(Integer pageNum, Integer pageSize, HouseQueryVo houseQueryVo);
}
