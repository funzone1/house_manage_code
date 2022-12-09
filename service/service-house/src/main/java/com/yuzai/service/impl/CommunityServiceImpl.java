package com.yuzai.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yuzai.dao.BaseDao;
import com.yuzai.dao.CommunityDao;
import com.yuzai.dao.DictDao;
import com.yuzai.entity.Community;
import com.yuzai.entity.Dict;
import com.yuzai.service.CommunityService;
import com.yuzai.util.CastUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = CommunityService.class)
@Transactional
public class CommunityServiceImpl extends BaseServiceImpl<Community> implements CommunityService {

    @Autowired
    private CommunityDao communityDao;

    @Autowired
    private DictDao dictDao;
    @Override
    protected BaseDao<Community> getEntityDao() {
        return communityDao;
    }

    @Override
    public List<Dict> findListByDictCode(String dictCode) {

        Dict dict = dictDao.getByDictCode(dictCode);
        List<Dict> listByParentId = dictDao.findListByParentId(dict.getId());
        return listByParentId;
    }

    @Override
    public List<Community> findAll() {
        return communityDao.findAll();
    }

    @Override
    public PageInfo<Community> findPage(Map<String, Object> filters) {

        //当前页数
        int pageNum = CastUtil.castInt(filters.get("pageNum"), 1);
        //每页显示的记录条数
        int pageSize = CastUtil.castInt(filters.get("pageSize"), 10);

        PageHelper.startPage(pageNum, pageSize);
        Page<Community> page = communityDao.findPage(filters);
        List<Community> list = page.getResult();
        for (Community community : list) {
            String areaName = dictDao.getNameById(community.getAreaId());
            String plateName = dictDao.getNameById(community.getPlateId());
            community.setAreaName(areaName);
            community.setPlateName(plateName);
        }
        return new PageInfo<Community>(page,10);
    }

    @Override
    public Community getById(Serializable id) {
        Community community = communityDao.getById(id);

        String areaName = dictDao.getNameById(community.getAreaId());
        String plateName = dictDao.getNameById(community.getPlateId());
        community.setAreaName(areaName);
        community.setPlateName(plateName);
        return community;

    }
}
