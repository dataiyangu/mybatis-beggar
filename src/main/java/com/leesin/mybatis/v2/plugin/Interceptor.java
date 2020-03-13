package com.leesin.mybatis.v2.plugin;

/**
 * @description:拦截器接口，所有自定义拦截器必须实现此接口
 * @author: Leesin Dong
 * @date: Created in 2020/3/12 22:28
 * @version:
 * @modified By:
 */
public interface Interceptor {
    /**
     * @description: 插件的核心逻辑实现
     * @name: intercept
     * @param: invocation
     * @return: java.lang.Object
     * @date: 2020/3/12 22:30
     * @auther: Administrator
    **/
    Object intercept(Invocation invocation)throws Throwable;

    /**
     * @description: 被拦截对象进行代理
     * @name: plugin
     * @param: target
     * @return: java.lang.Object
     * @date: 2020/3/12 22:30
     * @auther: Administrator
    **/
    Object plugin(Object target);
}
