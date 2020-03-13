package com.leesin.mybatis.v2.plugin;

import com.leesin.mybatis.v2.annotation.Intercepts;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @description:代理类 用于代理被拦截的对象
 * 同时提供了创建代理类的方法
 * @author: Leesin Dong
 * @date: Created in 2020/3/12 22:36
 * @version:
 * @modified By:
 */
public class Plugin implements InvocationHandler {
    private Object target;
    private Interceptor interceptor;

    /**
     *
     * @param target 被代理对象
     * @param interceptor 拦截器（插件）
     */
    
   
    public Plugin(Object target, Interceptor interceptor) {
        this.target = target;
        this.interceptor = interceptor;
    }

    /**
     * 对被代理对象进行代理，返回代理类
     * @param obj
     * @param interceptor
     * @return
     */
    public static Object wrap(Object obj, Interceptor interceptor) {
        Class<?> clazz = obj.getClass();
        return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), new Plugin(obj, interceptor));
    }

    /**
     * @description:
     * @name: invoke
     * @param: proxy
     * @param: method
     * @param: args
     * @return: java.lang.Object
     * @date: 2020/3/13 7:14
     * @auther: Administrator
    **/
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 自定义的插件上有@Intercepts注解，指定了拦截的方法
        if (interceptor.getClass().isAnnotationPresent(Intercepts.class)) {
            // 如果是被拦截的方法，则进入自定义拦截器的逻辑
            if (method.getName().equals(interceptor.getClass().getAnnotation(Intercepts.class).value())) {
                return  interceptor.intercept(new Invocation(target, method, args));
            }
        }
        // 非被拦截方法，执行原逻辑
        return method.invoke(target, method, args);
    }
}
