package com.leetai.mapping;

import com.leetai.modle.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userId);

    User selectByUsername(String username);

    
    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}