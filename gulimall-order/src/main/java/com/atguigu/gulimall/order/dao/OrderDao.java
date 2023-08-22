package com.atguigu.gulimall.order.dao;

import com.atguigu.gulimall.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author SingKwan
 * @email sunlightcs@gmail.com
 * @date 2023-06-06 16:31:53
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
