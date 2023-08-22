package com.atguigu.gulimall.product.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @projectName: gulimall
 * @package: com.atguigu.gulimall.product.config
 * @className: MyBatisConfig
 * @author: SingKwan
 * @description: TODO
 * @date: 2023/6/21 13:10
 * @version: 1.0
 */
@Configuration//配置类
@EnableTransactionManagement//开启事务
@MapperScan("com.atguigu.gulimall.product.dao")
public class MyBatisConfig {

    //引入分页插件
    @Bean
    public PaginationInterceptor PaginationInterceptor() {
        PaginationInterceptor paginationInterceptor=new PaginationInterceptor();
        //设置请求的页面大于最大页后操作，true调回到首页，false继续请求，默认false
        paginationInterceptor.setOverflow(true);
        //设置最大单页限制数量，默认500条，-1不受限制
        paginationInterceptor.setLimit(100);
        return paginationInterceptor;
    }
}
