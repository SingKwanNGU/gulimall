package com.atguigu.gulimall.thirdparty.controller;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.atguigu.common.utils.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @projectName: gulimall
 * @package: com.atguigu.gulimall.thirdparty.controller
 * @className: OssController
 * @author: SingKwan
 * @description: TODO
 * @date: 2023/6/15 23:30
 * @version: 1.0
 */
@RestController
public class OssController {

    @Resource
    OSSClient ossClient;

    @Value("${alibaba.cloud.oss.endpoint}")
    private String endpoint;



    @Value("${alibaba.cloud.access-key}")
    private  String accessid;

    @RequestMapping("/oss/policy")
    public R policy(){
        //https://gulimall-singkwan.oss-cn-beijing.aliyuncs.com/syz.jpg
        String bucket="gulimall-singkwan";
        String host="https://"+bucket+"."+endpoint;
//        String callbackUrl="";
        String format=new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        String dir=format+"/";

        Map<String,String> respMap=null;

        try{
            long expireTime=30;
            long expireEndTime=System.currentTimeMillis()+expireTime*1000;
            Date expiration = new Date(expireEndTime);
            PolicyConditions policyConds= new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE,0,1048576000);
            policyConds.addConditionItem(MatchMode.StartWith,PolicyConditions.COND_KEY,dir);

            String postPolicy=ossClient.generatePostPolicy(expiration,policyConds);
            byte[] binaryData=postPolicy.getBytes("utf-8");
            String encodePolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature=ossClient.calculatePostSignature(postPolicy);

            respMap=new LinkedHashMap<String,String>();
            respMap.put("accessid",accessid);
            respMap.put("policy",encodePolicy);
            respMap.put("signature",postSignature);
            respMap.put("dir",dir);
            respMap.put("host",host);
            respMap.put("expire",String.valueOf(expireEndTime/1000));


        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        return R.ok().put("data",respMap);
    }


}
