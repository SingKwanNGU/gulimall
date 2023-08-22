package com.atguigu.common.to;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @projectName: gulimall
 * @package: com.atguigu.common.to
 * @className: SkuReductionTo
 * @author: SingKwan
 * @description: TODO
 * @date: 2023/6/26 20:28
 * @version: 1.0
 */
@Data
public class SkuReductionTo {
    private Long skuId;
    private int fullCount;
    private BigDecimal discount;
    private int countStatus;
    private BigDecimal fullPrice;
    private BigDecimal reducePrice;
    private int priceStatus;
    private List<MemberPrice> memberPrice;
}
