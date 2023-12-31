package com.atguigu.common.constant;

/**
 * @projectName: gulimall
 * @package: com.atguigu.constant
 * @className: ProductConstant
 * @author: SingKwan
 * @description: TODO
 * @date: 2023/6/22 16:29
 * @version: 1.0
 */
public class ProductConstant {

    public enum AttrEnum{
        ATTR_TYPE_BASE(1,"基本属性"),ATTR_TYPE_SALE(0,"销售属性");

        private int code;
        private String msg;

        AttrEnum(int code,String msg){
            this.code=code;
            this.msg=msg;
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }

}
