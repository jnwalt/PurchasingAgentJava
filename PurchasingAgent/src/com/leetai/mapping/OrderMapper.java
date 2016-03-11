package com.leetai.mapping;

import java.util.List;

import com.leetai.modle.Order;

public interface OrderMapper {
	int deleteByPrimaryKey(Integer psId);

	int insert(Order record);

	int insertSelective(Order record);

	Order selectByPrimaryKey(Integer psId);

	int updateByPrimaryKeySelective(Order record);

	int updateByPrimaryKey(Order record);

	// //////////////
	List<Order> selectAllByUserId(Integer userId);
	List<Order> selectAllByStatus(Integer userId,Integer status);
	Order selectBySId(Integer sId);
	 }