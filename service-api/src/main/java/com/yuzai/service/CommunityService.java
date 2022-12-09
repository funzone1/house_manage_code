package com.yuzai.service;

import com.yuzai.entity.Community;
import com.yuzai.entity.Dict;

import java.util.List;

public interface CommunityService extends BaseService<Community>{
    List<Dict> findListByDictCode(String dictCode);

    List<Community> findAll();
}
