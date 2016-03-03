package com.leetai.mapping;

import com.leetai.modle.Order;

public interface OrderMapper {
    int deleteByPrimaryKey(Integer psId);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer psId);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
}