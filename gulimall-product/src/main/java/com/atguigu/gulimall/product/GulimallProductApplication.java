package com.atguigu.gulimall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @description TODO 整合Mybatis-Plus
 * 1.导入依赖
 * <dependency>
 *             <groupId>com.baomidou</groupId>
 *             <artifactId>mybatis-plus-boot-starter</artifactId>
 *             <version>3.2.0</version>
 * </dependency>
 * 2.配置
 *    1）配置数据源：
 *    2）配置Mybatis-Plus
 *
 * 3.逻辑删除
 *  3.1) 配置全局的逻辑删除规则（可以省略）
 *  3.2）配置逻辑删除的组件Bean(3.1.1版本以上BP可以省略)
 *  3.3）加上逻辑删除注解@TableLogic
 *
 * 4.JSR303
 *   4.1)给bean添加校验注解 javax.validation.constraints校验器相关
 *   4.2）给需要进行校验的querystring添加@Valid注解开启校验  效果：校验错误以后会有默认的响应
 *   4.3）给校验后的bean紧跟一个BindingResult，就可以获取到校验的结果
 *   4.4) 分组校验（实现多场景复杂校验）：
 *         1）给属性上添加注解@Null(message = "新增不能指定id",groups = UpdateGroup.class)
 *         给校验注解标注什么情况需要进行校验
 *         2）使用注解@Validated中的value属性来指定分组
 *         如@Validated({AddGroup.class})
 *         3)默认没有指定分组校验注解@NotBlank,在@Validated({AddGroup.class})分组校验情况下不生效，只会在@Validated且不指定分组这种情况下生效
 *   4.5）自定义校验
 *       1）编写一个自定义的校验注解
 *       2) 编写一个自定义的校验器
 *       3）关联自定义的校验器和自定义的校验注解
 *       @Documented
 *       @Constraint(
 *               validatedBy = {ListValueConstraintValidator.class}
 *       )
 *       @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
 *       @Retention(RetentionPolicy.RUNTIME)
 *       public @interface ListValue {
 *
 *   5.统一的异常处理
 * @ControllerAdvice
 *   1)编写异常处理类，使用@ControllerAdvice
 *   2）使用@ExceptionHandler标注方法可以处理的异常
 * @date 2023/5/30 18:43
 *
 *
 */
@EnableFeignClients(basePackages = "com.atguigu.gulimall.product.feign")
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("com.atguigu.gulimall.product.dao")
public class GulimallProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimallProductApplication.class, args);
    }

}
