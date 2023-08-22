package com.atguigu.gulimall.product.vo;

import lombok.Data;

/**
 * @projectName: gulimall
 * @package: com.atguigu.gulimall.product.vo
 * @className: AttrRespVo
 * @author: SingKwan
 * @description: TODO
 * @date: 2023/6/21 19:52
 * @version: 1.0
 */
@Data
public class AttrRespVo extends AttrVo{

//    "catelogName": "手机/数码/手机", //所属分类名字
//            "groupName": "主体", //所属分组名字
    private String catelogName;

    private String groupName;

    private Long[] catelogPath;


}
