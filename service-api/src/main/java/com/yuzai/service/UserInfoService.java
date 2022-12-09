package com.yuzai.service;

import com.yuzai.entity.UserInfo;

public interface UserInfoService extends BaseService<UserInfo>{

    UserInfo getByPhone(String phone);
}
