package com.leesin.mybatis.v2.bingding;

import com.leesin.mybatis.v1.MapperProxy;
import com.leesin.mybatis.v2.session.DefaultSqlSession;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: 维护接口和工厂类的关系，用于获取MapperProxy代理对象
 * 工厂类制定了POJO类型，用于处理结果集返回
 * @author: Leesin Dong
 * @date: Created in 2020/3/12 19:16
 * @version:
 * @modified By:
 */

public class MapperRegistry {

    //    接口和工厂类映射关系
    private final Map<Class<?>, MapperProxyFactory> knowMappers = new HashMap<Class<?>, MapperProxyFactory>();

    public <T> void addMapper(Class<T> clazz, Class pojo) {
        knowMappers.put(clazz, new MapperProxyFactory(clazz, pojo));
    }

    public <T> T getMapper(Class<T> clazz, DefaultSqlSession sqlSession) {
        MapperProxyFactory mapperProxyFactory = knowMappers.get(clazz);
        if (mapperProxyFactory == null) {
            throw new RuntimeException("Type: " + clazz + " can not find");
        }
        return (T) mapperProxyFactory.newInstance(sqlSession);
    }
}
