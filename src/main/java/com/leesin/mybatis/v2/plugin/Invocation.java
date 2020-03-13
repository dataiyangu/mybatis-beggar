package com.leesin.mybatis.v2.plugin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @description:包装类，对被代理对象进行包装
 * @author: Leesin Dong
 * @date: Created in 2020/3/12 22:34
 * @version:
 * @modified By:
 */
public class Invocation {
    private Object target;
    private Method method;
    private Object[] args;
    public Invocation(Object target, Method method, Object[] args) {
        this.target = target;
        this.method = method;
        this.args = args;
    }

    public Object getTarget() {
        return target;
    }

    public Method getMethod() {
        return method;
    }

    public Object[] getArgs() {
        return args;
    }
    //    执行自己本身的方法
    public Object proceed() throws InvocationTargetException,IllegalAccessException {
        return method.invoke(target,args);
    }

}
