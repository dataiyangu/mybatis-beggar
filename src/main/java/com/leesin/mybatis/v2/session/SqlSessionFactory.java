package com.leesin.mybatis.v2.session;


/**
 * @description:
 * @author: Leesin Dong
 * @date: Created in 2020/3/12 17:37
 * @version:
 * @modified By:
 */
public class SqlSessionFactory {
    private Configuration configuration;

    /**
     * @description: 初始化configuration，解析配置文件中的工作在configuration的构造函数中
     * @name: build
     * @param:
     * @return: com.leesin.mybatis.v2.session.SqlSessionFactory
     * @date: 2020/3/12 17:38
     * @auther: Administrator
    **/
    public SqlSessionFactory build() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        configuration = new Configuration();
        return this;
    }

    /**
     * @description: 获取DeafaultSqlSession
     * @name: openSqlSession
     * @param:
     * @return: DefaultSqlSessoin
     * @date: 2020/3/12 17:41
     * @auther: Administrator
    **/
    public DefaultSqlSession openSqlSession() {
        return new DefaultSqlSession(configuration);
    }
}
