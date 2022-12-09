package com.yuzai.service;

import com.yuzai.entity.Dict;

import java.util.List;
import java.util.Map;

public interface DictService {

    List<Map<String, Object>> findZnodes(Long id);

    List<Dict> findListByParentId(Long id);

    /**
     * 根据编码获取节点数据列表
     * @param dictCode
     * @return
     */
    List<Dict> findListByDictCode(String dictCode);
}
