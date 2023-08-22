package com.atguigu.gulimall.coupon.dao;

import com.atguigu.gulimall.coupon.entity.CouponEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券信息
 * 
 * @author SingKwan
 * @email sunlightcs@gmail.com
 * @date 2023-06-06 15:28:58
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {
	
}
