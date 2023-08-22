package com.atguigu.gulimall.member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @description TODO 想要远程调用别的服务
 * todo 1) 引入openfeign依赖
 * todo 2) 编写一个接口，告诉springcloud这个接口需要调用远程服务
 * todo   2.1 明确声明接口的每一个方法都是调用哪个远程服务的哪个请求
 * todo 3) 使用注解@EnableFeignClients开启远程调用功能
 * @date 2023/6/6 23:38
 */
@EnableFeignClients(basePackages = "com.atguigu.gulimall.member.feign")
@EnableDiscoveryClient
@SpringBootApplication
public class GulimallMemberApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimallMemberApplication.class, args);
    }

}
