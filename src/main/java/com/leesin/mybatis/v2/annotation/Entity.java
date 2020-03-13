package com.leesin.mybatis.v2.annotation;

/**
 * @description:
 * @author: Leesin Dong
 * @date: Created in 2020/3/12 16:22
 * @version:
 * @modified By:
 */

import java.lang.annotation.*;

/**
 * @description: 用于注解接口，以反映返回的实体类
 * @name:
 * @param: null
 * @return:
 * @date: 2020/3/12 16:22
 * @auther: Administrator
**/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Entity {
    //Class<?> 是类型的意思
    Class<?> value();
}
