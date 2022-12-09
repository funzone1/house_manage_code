package com.yuzai.service;

import com.github.pagehelper.PageInfo;
import com.yuzai.entity.UserFollow;
import com.yuzai.vo.UserFollowVo;

public interface UserFollowService extends BaseService<UserFollow> {
    void follow(Long houseId, Long userId);

    Boolean isFollowed(Long id, Long id1);

    PageInfo<UserFollowVo> findPageList(Integer pageNum, Integer pageSize, Long userId);

    void cancelFollow(Long id);
}
