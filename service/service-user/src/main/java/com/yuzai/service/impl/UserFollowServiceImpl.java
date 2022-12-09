package com.yuzai.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yuzai.dao.BaseDao;
import com.yuzai.dao.UserFollowDao;
import com.yuzai.entity.House;
import com.yuzai.entity.UserFollow;
import com.yuzai.service.HouseService;
import com.yuzai.service.UserFollowService;
import com.yuzai.vo.UserFollowVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service(interfaceClass = UserFollowService.class)
@Transactional
public class UserFollowServiceImpl extends BaseServiceImpl<UserFollow> implements UserFollowService {

    @Autowired
    private UserFollowDao userFollowDao;

    @Reference
    private HouseService houseService;

    @Override
    protected BaseDao<UserFollow> getEntityDao() {
        return userFollowDao;
    }

    @Override
    public void follow(Long houseId, Long userId) {
        UserFollow userFollow = new UserFollow();
        userFollow.setHouseId(houseId);
        userFollow.setUserId(userId);
        userFollowDao.insert(userFollow);
    }

    @Override
    public Boolean isFollowed(Long userId, Long houseId) {
        Integer count = userFollowDao.getCountByUserIdAndHouseId(userId,houseId);
        if(count > 0){
            return true;
        }else {
            return false;
        }

    }

    @Override
    public PageInfo<UserFollowVo> findPageList(Integer pageNum, Integer pageSize, Long userId) {
        PageHelper.startPage(pageNum,pageSize);
        Page<UserFollowVo> page = userFollowDao.findPageList(userId);
        for (UserFollowVo userFollowVo : page) {
            House house = houseService.getById(userFollowVo.getHouseId());
            userFollowVo.setHouseTypeName(house.getHouseTypeName());
            userFollowVo.setDirectionName(house.getDirectionName());
            userFollowVo.setFloorName(house.getFloorName());
        }
        return new PageInfo<UserFollowVo>(page,5);
    }

    @Override
    public void cancelFollow(Long id) {
        userFollowDao.delete(id);
    }
}
