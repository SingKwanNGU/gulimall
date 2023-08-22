package com.atguigu.common.valid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Set;

/**
 * @projectName: gulimall
 * @package: com.atguigu.valid
 * @className: ListValueConstraintValidator
 * @author: SingKwan
 * @description: TODO
 * @date: 2023/6/16 22:25
 * @version: 1.0
 */
public class ListValueConstraintValidator implements ConstraintValidator<ListValue,Integer> {
    private Set<Integer> set =new HashSet<>();
    @Override//初始化方法
    public void initialize(ListValue constraintAnnotation) {
        int[] vals = constraintAnnotation.vals();
        if(vals!=null){
            for (int val: vals){
                set.add(val);
            }
        }
    }

    /**
     * @param integer: 需要校验的值
     * @param constraintValidatorContext:
     * @return boolean
     * @author perse
     * @description TODO
     * @date 2023/6/16 22:31
     */

    @Override//判断校验方法
    public boolean isValid(Integer integer, ConstraintValidatorContext constraintValidatorContext) {
        return set.contains(integer);
    }
}
