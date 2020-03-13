package com.leesin.mybatis.v2.Executor;

import com.leesin.mybatis.v2.cache.Cachekey;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:带缓存的执行器，用于装饰基本执行器
 * @author: Leesin Dong
 * @date: Created in 2020/3/12 20:16
 * @version:
 * @modified By:
 */
public class CachingExecutor {
    private Executor delegate;
    private static final Map<Integer, Object> cache = new HashMap<>();

    public CachingExecutor(Executor delegate) {
        this.delegate = delegate;
    }

    public <T> T query(String statement, Object[] parameter, Class pojo) {
    //    计算cachekey
        Cachekey cachekey = new Cachekey();
        cachekey.update(statement);
        cachekey.update(joinStr(parameter));
        //是否拿到缓存
        if (cache.containsKey(cachekey.getCode())) {
            //    命中缓存
            System.out.println("【命中缓存】");
            return (T)cache.get(cachekey.getCode());
        } else {
            Object object = delegate.query(statement, parameter, pojo);
            cache.put(cachekey.getCode(), object);
            return (T) object;
        }
    }

    // 为了命中缓存，把Object[]转换成逗号拼接的字符串，因为对象的HashCode都不一样
    public String joinStr(Object[] objs){
        StringBuffer sb = new StringBuffer();
        if(objs !=null && objs.length>0){
            for (Object objStr : objs) {
                sb.append(objStr.toString() + ",");
            }
        }
        int len = sb.length();
        if(len >0){
            sb.deleteCharAt(len-1);
        }
        return  sb.toString();
    }
}
