package com.atguigu.gulimall.product.dao;

import com.atguigu.gulimall.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author SingKwan
 * @email sunlightcs@gmail.com
 * @date 2023-05-30 15:33:46
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
