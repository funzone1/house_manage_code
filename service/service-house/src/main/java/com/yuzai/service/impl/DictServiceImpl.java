package com.yuzai.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.yuzai.dao.BaseDao;
import com.yuzai.dao.DictDao;
import com.yuzai.entity.Dict;
import com.yuzai.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = DictService.class)
@Transactional
public class DictServiceImpl implements DictService {

    @Autowired
    private DictDao dictDao;



    @Override
    public List<Map<String, Object>> findZnodes(Long id) {
        // 返回数据[{ id:2, isParent:true, name:"随意勾选 2"}]
        List<Dict> list = dictDao.findListByParentId(id);
        List<Map<String, Object>> zNodes = new ArrayList<>();
        for (Dict dict : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("id",dict.getId());
            map.put("name",dict.getName());
            Integer count = dictDao.IsParent(dict.getId());
            map.put("isParent",count > 0 ? true : false);
            zNodes.add(map);
        }
        return zNodes;
    }

    @Override
    public List<Dict> findListByParentId(Long id) {
        return dictDao.findListByParentId(id);
    }

    @Override
    public List<Dict> findListByDictCode(String dictCode) {
        Dict dict = dictDao.getByDictCode(dictCode);
        if(null == dict) return null;
        List<Dict> listByParentId = dictDao.findListByParentId(dict.getId());
        return listByParentId;
    }
}
