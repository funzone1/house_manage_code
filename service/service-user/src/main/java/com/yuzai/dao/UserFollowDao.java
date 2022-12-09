package com.yuzai.dao;

import com.github.pagehelper.Page;
import com.yuzai.entity.UserFollow;
import com.yuzai.vo.UserFollowVo;
import org.apache.ibatis.annotations.Param;

public interface UserFollowDao extends BaseDao<UserFollow>{
    Integer getCountByUserIdAndHouseId(@Param("userId") Long userId, @Param("houseId") Long houseId);

    Page<UserFollowVo> findPageList(Long userId);
}
