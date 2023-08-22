package com.atguigu.gulimall.product;


import com.atguigu.gulimall.product.entity.BrandEntity;
import com.atguigu.gulimall.product.service.BrandService;
import com.atguigu.gulimall.product.service.CategoryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.Arrays;
import java.util.List;
/**
 * @description TODO aliyun oss上传文件步骤
 *   todo 1）引入依赖 <dependency>
 *             <groupId>com.alibaba.cloud</groupId>
 *             <artifactId>aliyun-oss-spring-boot-starter</artifactId>
 *             <exclusions>
 *                 <exclusion>
 *                     <groupId>com.aliyun</groupId>
 *                     <artifactId>aliyun-java-sdk-core</artifactId>
 *                 </exclusion>
 *             </exclusions>
 *         </dependency>
 *         以及在下面引入阿里云依赖
 *          <dependencyManagement>
 *         <dependencies>
 *             <dependency>
 *                 <groupId>com.alibaba.cloud</groupId>
 *                 <artifactId>aliyun-spring-boot-dependencies</artifactId>
 *                 <version>1.0.0</version>
 *                 <type>pom</type>
 *                 <scope>import</scope>
 *             </dependency>
 *         </dependencies>
 *     </dependencyManagement>
 *
 *   todo 2） 配置key,endpoint
 *
 *   todo 3)依赖注入 OSSClient 进行相关操作
 * @date 2023/6/15 20:39
 */
@Slf4j
@SpringBootTest
public class GulimallProductApplicationTests {

    @Autowired
    BrandService brandService;

    @Autowired
    CategoryService categoryService;

    @Test
    public void testFindPath(){
        Long[] catelogPath = categoryService.findCatelogPath(225L);
        log.info("完整路径：{}", Arrays.asList(catelogPath));

    }



    @Test
    public void contextLoads() {
//        BrandEntity brandEntity = new BrandEntity();
//        brandEntity.setBrandId(1L);
//        brandEntity.setDescript("华为");
//        brandEntity.setName("华为");
//        brandService.save(brandEntity);
//        System.out.println("保存成功");
//
//        brandService.updateById(brandEntity);
        List<BrandEntity> list = brandService.list(new QueryWrapper<BrandEntity>().eq("brand_id", 1L));
        list.forEach(System.out::println);
    }


}
