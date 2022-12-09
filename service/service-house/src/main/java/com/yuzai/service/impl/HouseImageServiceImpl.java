package com.yuzai.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.yuzai.dao.BaseDao;
import com.yuzai.dao.HouseImageDao;
import com.yuzai.entity.HouseImage;
import com.yuzai.service.HouseImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = HouseImageService.class)
@Transactional
public class HouseImageServiceImpl extends BaseServiceImpl<HouseImage> implements HouseImageService {

    @Autowired
    private HouseImageDao houseImageDao;

    @Override
    public List<HouseImage> getImagesByHouseIdAndType(Long houseId, Integer type) {
        return houseImageDao.getImagesByHouseIdAndType(houseId,type);
    }

    @Override
    protected BaseDao<HouseImage> getEntityDao() {
        return houseImageDao;
    }
}
