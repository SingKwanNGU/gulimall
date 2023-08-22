package com.atguigu.gulimall.product.vo;

import com.atguigu.gulimall.product.entity.AttrEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.List;

/**
 * @projectName: gulimall
 * @package: com.atguigu.gulimall.product.vo
 * @className: AttrGroupWithAttrsVo
 * @author: SingKwan
 * @description: TODO
 * @date: 2023/6/23 20:10
 * @version: 1.0
 */
@Data
public class AttrGroupWithAttrsVo {

    private Long attrGroupId;
    /**
     * 组名
     */
    private String attrGroupName;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 描述
     */
    private String descript;
    /**
     * 组图标
     */
    private String icon;
    /**
     * 所属分类id
     */
    private Long catelogId;

    private List<AttrEntity> attrs;




}
