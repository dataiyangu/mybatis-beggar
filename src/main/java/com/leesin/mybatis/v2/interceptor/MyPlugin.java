package com.leesin.mybatis.v2.interceptor;

import com.leesin.mybatis.v2.annotation.Intercepts;
import com.leesin.mybatis.v2.plugin.Interceptor;
import com.leesin.mybatis.v2.plugin.Invocation;
import com.leesin.mybatis.v2.plugin.Plugin;

import java.util.Arrays;

/**
 * @description:自定义插件
 * @author: Leesin.Dong
 * @date: Created in 2020/3/13 16:05
 * @version:
 * @modified By:
 */
@Intercepts("query")
public class MyPlugin implements Interceptor {
    public  Object intercept(Invocation invocation) throws Throwable {
        String statement = (String) invocation.getArgs()[0];
        Object[] parameter = (Object[]) invocation.getArgs()[1];
        Class pojo = (Class) invocation.getArgs()[2];
        System.out.println("插件输出：SQL：["+statement+"]");
        System.out.println("插件输出：Parameters："+ Arrays.toString(parameter));

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }
}
