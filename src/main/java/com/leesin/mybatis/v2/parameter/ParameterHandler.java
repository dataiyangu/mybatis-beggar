package com.leesin.mybatis.v2.parameter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @description:参数处理器
 * @author: Leesin.Dong
 * @date: Created in 2020/3/13 15:58
 * @version:
 * @modified By:
 */
public class ParameterHandler {
    private PreparedStatement psmt;
    public ParameterHandler(PreparedStatement statement) {
        this.psmt = statement;
    }

    /**
     * 从方法中获取参数，遍历设置SQL中的？占位符
     * @param parameters
     */
    public void setParameters(Object[] parameters) {
        for (int i = 0; i < parameters.length; i++) {
            int k = i + 1;
            try {
                if (parameters[i] instanceof Integer) {
                    psmt.setInt(k, (Integer) parameters[i]);
                } else if (parameters[i] instanceof Long) {
                    psmt.setLong(k, (Long) parameters[i]);
                } else if (parameters[i] instanceof String) {
                    psmt.setString(k , String.valueOf(parameters[i]));
                } else if (parameters[i] instanceof Boolean) {
                    psmt.setBoolean(k, (Boolean) parameters[i]);
                } else {
                    psmt.setString(k, String.valueOf(parameters[i]));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
