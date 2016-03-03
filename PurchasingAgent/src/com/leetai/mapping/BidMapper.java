package com.leetai.mapping;

import java.util.List;

import com.leetai.modle.Bid;

public interface BidMapper {
    int deleteByPrimaryKey(Integer sId);

    int insert(Bid record);

    int insertSelective(Bid record);

    Bid selectByPrimaryKey(Integer sId);

    int updateByPrimaryKeySelective(Bid record);

    int updateByPrimaryKey(Bid record);
    /////////////////////
    List<Bid> findByPId(Integer pId);
}