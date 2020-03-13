package com.leesin.mybatis.v1;

import java.lang.reflect.Proxy;
import java.util.ResourceBundle;

/**
 * @description:
 * @author: Leesin Dong
 * @date: Created in 2020/3/12 11:16
 * @version:
 * @modified By:
 */
public class Configuration {

    public static final ResourceBundle sqlMapping;
    static{
        sqlMapping = ResourceBundle.getBundle("mysql");
    }

    public <T> T getMapper(Class<T> clazz, SqlSession sqlSession) {

        return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(),
                new Class[]{clazz}, new MapperProxy(sqlSession));
    }
}
