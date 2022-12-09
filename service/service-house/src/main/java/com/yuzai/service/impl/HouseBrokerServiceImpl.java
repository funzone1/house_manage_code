package com.yuzai.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.yuzai.dao.BaseDao;
import com.yuzai.dao.HouseBrokerDao;
import com.yuzai.entity.HouseBroker;
import com.yuzai.service.HouseBrokerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = HouseBrokerService.class)
@Transactional
public class HouseBrokerServiceImpl extends BaseServiceImpl<HouseBroker> implements HouseBrokerService {

    @Autowired
    private HouseBrokerDao houseBrokerDao;

    @Override
    public List<HouseBroker> getBrokersByHouseId(Long houseId) {
        return houseBrokerDao.getBrokersByHouseId(houseId);
    }

    @Override
    protected BaseDao<HouseBroker> getEntityDao() {
        return houseBrokerDao;
    }
}
