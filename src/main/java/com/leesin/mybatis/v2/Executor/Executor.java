package com.leesin.mybatis.v2.Executor;

/**
 * @description:
 * @author: Leesin Dong
 * @date: Created in 2020/3/12 20:14
 * @version:
 * @modified By:
 */
public interface Executor {
    <T> T query(String statement, Object[] parameter,Class pojo);
}
