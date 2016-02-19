package com.leetai.mapping;

import com.leetai.modle.Version;

public interface VersionMapper {
    int insert(Version record);

    int insertSelective(Version record);
    
    Version select();
}