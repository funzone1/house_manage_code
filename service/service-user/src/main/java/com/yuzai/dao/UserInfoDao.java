package com.yuzai.dao;

import com.yuzai.entity.UserInfo;

public interface UserInfoDao extends BaseDao<UserInfo>{
    UserInfo getByPhone(String phone);
}
