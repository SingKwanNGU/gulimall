package com.atguigu.gulimall.ware.vo;

import lombok.Data;

/**
 * @projectName: gulimall
 * @package: com.atguigu.gulimall.ware.vo
 * @className: PurchaseItemDoneVo
 * @author: SingKwan
 * @description: TODO
 * @date: 2023/6/29 17:09
 * @version: 1.0
 */
@Data
public class PurchaseItemDoneVo {

    private Long itemId;
    private Integer status;
    private String reason;

}
