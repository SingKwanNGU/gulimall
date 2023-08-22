package com.atguigu.gulimall.ware.feign;

import com.atguigu.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @projectName: gulimall
 * @package: com.atguigu.gulimall.ware.feign
 * @className: ProductFeignService
 * @author: SingKwan
 * @description: TODO
 * @date: 2023/6/29 20:30
 * @version: 1.0
 */
@FeignClient("gulimall-product")
public interface ProductFeignService {

    //远程获取sku信息

    /**
     *   远程接口有两种写法：
     *   1.让所有请求过网关：
     *          1）@FeignClient("gulimall-gateway")：给gulimall-gateway所在的机器发请求
     *          2）  /api//product/skuinfo/info/{skuId}
     *    这样网关会根据请求路径转发给gulimall-product微服务
     *
     *    2.直接让后台指定服务处理
     *          1）@FeignClient("gulimall-product")
     *          2）  /product/skuinfo/info/{skuId}
     *     这样后台会直接找gulimall-product里的控制器controller再根据映射找对应处理方法handlerMethod
     */
    @RequestMapping("/product/skuinfo/info/{skuId}")
    public R info(@PathVariable("skuId") Long skuId);


}
