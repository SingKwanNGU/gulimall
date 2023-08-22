package com.atguigu.gulimall.coupon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
/**
 * @description TODO 如何使用Nacos作为配置中心统一管理配置
 * todo 1）引入依赖   <!--Nacos配置中心依赖-->
 *        1 <dependency>
 *             <groupId>com.alibaba.cloud</groupId>
 *             <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
 *         </dependency>
 *         <!-- https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-bootstrap -->
 *         <!--当前nacos版本为2.23，需要引入bootstrap依赖-->
 *        2 <dependency>
 *             <groupId>org.springframework.cloud</groupId>
 *             <artifactId>spring-cloud-starter-bootstrap</artifactId>
 *             <version>3.1.5</version>
 *         </dependency>
 * todo 2)创建bootstrap.properties文件 并配置1.应用名称 2.nacos服务器地址及端口
 *  spring.application.name=gulimall-coupon
 *  spring.cloud.nacos.config.server-addr=127.0.0.1:8848
 * todo 3)需要给配置中心默认添加一个数据集（Data Id）gulimall-coupon.properties。起名默认规则为：应用名.properties
 * todo 4）编辑应用名.properties，添加自己所需的配置
 * todo 5)动态获取配置  添加注解@RefreshScope到controller层和@Value到需要的配置项上。
 *  如果配置中心和当前应用的配置文件都配置了相同的配置项，配置中心的配置文件优先生效。
 *           TODO 细节
 *  todo 1）命名空间：配置隔离；
 *          默认：public(保留空间); 默认新增的所有配置都在public空间
 *          1、开发、测试、生产空间的配置,并利用命名空间来实现环境隔离。
 *           注意：在bootstrap.properties中配置使用命名空间ID来明确使用哪一个命名空间
 *           如：spring.cloud.nacos.config.namespace=2914b049-4f80-438e-8d9d-2056289da1d4（命名空间ID）
 *          2、 每一个微服务之间互相隔离配置，每一个微服务都创建一个自己的命名空间，这样可以只加载自己命名空间里的配置
 *  todo 2) 配置集：所有的配置的集合
 *  todo 3) 配置集ID：类似配置文件名
 *          Data ID:类似配置文件名
 *  todo 4) 配置分组：默认所有的配置集都属于DEFAULT_GROUP
 *           注意：在bootstrap.properties中使用config.group来明确使用哪一个分组的配置
 *
 *  TODO 每个微服务创建自己的命名空间，使用配置分组区分环境，dev、test、prod
 *       将微服务的配置文件拆分并放在nacos配置中心，在nacos配置中心配置多个配置集，并在bootstrap.properties中添加要加载的配置集
 *       如：spring.cloud.nacos.config.extension-configs[0].data-id=datasource.yml
 *          spring.cloud.nacos.config.extension-configs[0].group=dev
 *          spring.cloud.nacos.config.extension-configs[0].refresh=true（这个是启动刷新）
 *
 * @date 2023/6/7 10:59
 */
@SpringBootApplication
@EnableDiscoveryClient
public class GulimallCouponApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimallCouponApplication.class, args);
    }

}
