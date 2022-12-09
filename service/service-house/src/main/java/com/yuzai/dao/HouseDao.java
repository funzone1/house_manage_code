package com.yuzai.dao;

import com.github.pagehelper.Page;
import com.yuzai.entity.House;
import com.yuzai.vo.HouseQueryVo;
import com.yuzai.vo.HouseVo;

public interface HouseDao extends BaseDao<House>{
    Page<HouseVo> findListPage(HouseQueryVo houseQueryVo);
}
