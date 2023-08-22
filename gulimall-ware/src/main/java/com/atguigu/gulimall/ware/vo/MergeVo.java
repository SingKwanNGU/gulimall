package com.atguigu.gulimall.ware.vo;

import lombok.Data;

import java.util.List;

/**
 * @projectName: gulimall
 * @package: com.atguigu.gulimall.ware.vo
 * @className: MergeVo
 * @author: SingKwan
 * @description: TODO
 * @date: 2023/6/28 18:21
 * @version: 1.0
 */
@Data
public class MergeVo {

    private Long purchaseId;//整单id
    private List<Long> items;//[1,2,3,4] 合并项集合


}
