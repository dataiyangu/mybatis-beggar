package com.leesin.mybatis.v2.bingding;

import com.leesin.mybatis.v2.session.DefaultSqlSession;

import java.lang.reflect.Proxy;

/**
 * @description:用于生产MapperProxy代理类
 * @author: Leesin Dong
 * @date: Created in 2020/3/12 19:24
 * @version:
 * @modified By:
 */
public class MapperProxyFactory<T> {
    private Class<T> mapperInterface;
    private Class object;

    public MapperProxyFactory(Class<T> mapperInterface, Class object) {
        this.mapperInterface = mapperInterface;
        this.object = object;
    }

    public T newInstance(DefaultSqlSession sqlSession) {
        return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{mapperInterface}, new MapperProxy(sqlSession,object));
    }
}
