package com.leetai.mapping;

import java.util.List;

import com.leetai.modle.Publish;

public interface PublishMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(Publish record);

	int insertSelective(Publish record);

	Publish selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(Publish record);

	int updateByPrimaryKey(Publish record);
	
	/////////////////////
	List<Publish> findAll(Integer id);
}