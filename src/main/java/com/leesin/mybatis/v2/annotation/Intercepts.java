package com.leesin.mybatis.v2.annotation;

import javax.xml.bind.Element;
import java.lang.annotation.*;

/**
 * @description:注解拦截器，指定拦截方法
 * @author: Leesin Dong
 * @date: Created in 2020/3/12 16:36
 * @version:
 * @modified By:
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Intercepts {
    String value();
}
