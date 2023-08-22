package com.atguigu.common.constant;

/**
 * @projectName: gulimall
 * @package: com.atguigu.common.constant
 * @className: WareConstant
 * @author: SingKwan
 * @description: TODO
 * @date: 2023/6/28 19:10
 * @version: 1.0
 */
public class WareConstant {
    public enum PurchaseStatusEnum{
        CREATED(0,"新建"),ASSIGNED(1,"已分配"),
       RECEIVED(2,"已领取"),COMPELETED(3,"已完成"),
        HASERROR(4,"有异常");

        private int code;
        private String msg;

        PurchaseStatusEnum(int code,String msg){
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

    public enum PurchaseDetailStatusEnum{
        CREATED(0,"新建"),ASSIGNED(1,"已分配"),
        BUYING(2,"正在采购"),COMPELETED(3,"已完成"),
        HASERROR(4,"采购失败");

        private int code;
        private String msg;

        PurchaseDetailStatusEnum(int code,String msg){
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
