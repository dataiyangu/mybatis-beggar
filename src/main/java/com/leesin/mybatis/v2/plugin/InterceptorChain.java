package com.leesin.mybatis.v2.plugin;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:拦截器链，存放所有拦截器，和对代理对象进行循环代理
 * @author: Leesin Dong
 * @date: Created in 2020/3/12 22:28
 * @version:
 * @modified By:
 */
public class InterceptorChain {
    private final List<Interceptor> interceptors = new ArrayList<>();

    public void addInterceptor(Interceptor interceptor) {
        interceptors.add(interceptor);
    }

    /**
     * @description: 对被拦截的对象进行层层代理
     * @name: pluginAll
     * @param: target
     * @return: java.lang.Object
     * @date: 2020/3/12 22:33
     * @auther: Administrator
    **/
    public Object pluginAll(Object target) {
        for (Interceptor interceptor : interceptors) {
            target = interceptor.plugin(target);
        }
        return target;
    }

    public boolean hasPlugin() {
        if (interceptors.size() == 0) {
            return false;
        }
        return true;
    }
}
