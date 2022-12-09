package com.yuzai.dao;

import com.yuzai.entity.Dict;

import java.util.List;

public interface DictDao {
    List<Dict> findListByParentId(Long id);

    Integer IsParent(Long id);

    Dict getByDictCode(String dictCode);

    String getNameById(Long id);
}
