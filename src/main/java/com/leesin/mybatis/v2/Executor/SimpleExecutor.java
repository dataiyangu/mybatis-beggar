package com.leesin.mybatis.v2.Executor;

/**
 * @description:
 * @author: Leesin Dong
 * @date: Created in 2020/3/12 21:32
 * @version:
 * @modified By:
 */
public class SimpleExecutor implements Executor{
    @Override
    public <T> T query(String statement, Object[] parameter, Class pojo) {
        StatementHandler statementHandler = new StatementHandler();
        return statementHandler.query(statement, parameter, pojo);
    }
}
