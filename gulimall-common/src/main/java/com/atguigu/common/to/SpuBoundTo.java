package com.atguigu.common.to;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @projectName: gulimall
 * @package: com.atguigu.common.to
 * @className: SpuBoundTo
 * @author: SingKwan
 * @description: TODO
 * @date: 2023/6/26 19:32
 * @version: 1.0
 */
@Data
public class SpuBoundTo {

    private Long SpuId;
    private BigDecimal buyBounds;
    private BigDecimal growBounds;

}
