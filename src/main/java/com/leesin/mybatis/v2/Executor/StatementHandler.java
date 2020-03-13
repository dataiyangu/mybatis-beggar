package com.leesin.mybatis.v2.Executor;

import com.leesin.mybatis.v2.parameter.ParameterHandler;
import com.leesin.mybatis.v2.session.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 封装JDBC Statement，用于操作数据库
 */
public class StatementHandler {
    private ResultSetHandler resultSetHandler = new ResultSetHandler();

    public <T> T query(String statement, Object[] parameter, Class pojo) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Object result = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(statement);
            ParameterHandler parameterHandler = new ParameterHandler(preparedStatement);
            parameterHandler.setParameters(parameter);
            preparedStatement.execute();

            result = resultSetHandler.handle(preparedStatement.getResultSet(), pojo);

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                connection = null;
            }
        }
        //旨在try里面return会报错
        return null;

    }

    /**
     * @description:获取连接
     * @name: getConnection
     * @param:
     * @return: void
     * @date: 2020/3/13 15:27
     * @auther: Administrator
    **/

    private Connection getConnection() throws ClassNotFoundException {
        String driver = Configuration.properties.getString("jdbc.driver");
        String url =  Configuration.properties.getString("jdbc.url");
        String username = Configuration.properties.getString("jdbc.username");
        String password = Configuration.properties.getString("jdbc.password");
        Connection connection = null;
        Class.forName(driver);
        try {
            Connection connection1 = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
