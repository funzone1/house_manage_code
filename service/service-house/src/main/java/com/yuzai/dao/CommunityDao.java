package com.yuzai.dao;

import com.yuzai.entity.Community;

import java.util.List;

public interface CommunityDao extends BaseDao<Community>{

    List<Community> findAll();
}
