package com.atguigu.gulimall.ware.vo;

import com.atguigu.gulimall.ware.entity.PurchaseEntity;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @projectName: gulimall
 * @package: com.atguigu.gulimall.ware.vo
 * @className: PurchaseDoneVo
 * @author: SingKwan
 * @description: TODO
 * @date: 2023/6/29 17:06
 * @version: 1.0
 */
@Data
public class PurchaseDoneVo {
    @NotNull
    private Long id;
    private List<PurchaseItemDoneVo> items;


}
