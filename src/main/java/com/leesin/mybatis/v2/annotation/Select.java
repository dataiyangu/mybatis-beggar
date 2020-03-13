package com.leesin.mybatis.v2.annotation;

import java.lang.annotation.*;

/**
 * @description:
 * @author: Leesin Dong
 * @date: Created in 2020/3/12 17:33
 * @version:
 * @modified By:
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Select {
    String value();
}
