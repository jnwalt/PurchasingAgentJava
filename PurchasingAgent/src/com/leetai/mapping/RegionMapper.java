package com.leetai.mapping;

import java.util.List;

import com.leetai.modle.Region;

public interface RegionMapper {
    int deleteByPrimaryKey(Double sysRegionId);

    int insert(Region record);

    int insertSelective(Region record);

    Region selectByPrimaryKey(Double sysRegionId);

    int updateByPrimaryKeySelective(Region record);

    int updateByPrimaryKey(Region record);
    ////////////
    
    List<Region> findAll(Double parentId);
}