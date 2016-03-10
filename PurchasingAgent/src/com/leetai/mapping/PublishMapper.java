package com.leetai.mapping;

import java.util.List;

import com.leetai.modle.Publish;

public interface PublishMapper {
	
	
    int deleteByPrimaryKey(Integer pId);

    int insert(Publish record);

    int insertSelective(Publish record);

    Publish selectByPrimaryKey(Integer pId);

    int updateByPrimaryKeySelective(Publish record);

    int updateByPrimaryKey(Publish record);
    
     
	
	/////////////////////
 	List<Publish> findAllByUserId(Integer pUesrId);
 	List<Publish> findAll();
 	int updatePFlag(Integer pId);  
}