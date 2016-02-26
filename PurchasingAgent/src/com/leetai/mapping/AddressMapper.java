package com.leetai.mapping;

import java.util.List;

import com.leetai.modle.Address;

public interface AddressMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Address record);

    int insertSelective(Address record);

    Address selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Address record);

    int updateByPrimaryKey(Address record);
    
    ////////////
    List<Address>  findAll (Integer id);

}