package com.leesin.mybatis.v1;

/**
 * @description:
 * @author: Leesin Dong
 * @date: Created in 2020/3/12 11:16
 * @version:
 * @modified By:
 */
public class SqlSession {
    Configuration configuration;
    Executor executor;

    // GPSqlSession.java
    public SqlSession(Configuration configuration, Executor executor){
        this.configuration = configuration;
        this.executor = executor;
    }

    public <T> T selectOne(String statementId, Object parameter) {
        String sql = Configuration.sqlMapping.getString(statementId);
        if (null!=sql&&!"".equals(sql)) {
            return executor.query(sql, parameter);
        }
        return null;
    }

    public <T> T getMapper(Class<T> clazz) {
        return configuration.getMapper(clazz, this);
    }

}
