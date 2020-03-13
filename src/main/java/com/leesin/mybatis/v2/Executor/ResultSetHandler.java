package com.leesin.mybatis.v2.Executor;

import com.leesin.mybatis.v2.plugin.Plugin;
import com.sun.org.apache.xpath.internal.operations.String;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * @description:处理结果集
 * @author: Leesin Dong
 * @date: Created in 2020/3/13 7:55
 * @version:
 * @modified By:
 */
public class ResultSetHandler {
    public <T> T handle(ResultSet resultSet, Class type) throws IllegalAccessException, InstantiationException, SQLException {
        //直接调用class的方法产生一个实例
        Object pojo = null;

        pojo = type.newInstance();
        if (resultSet.next()) {
            for (Field declaredField : pojo.getClass().getDeclaredFields()) {
                setValue(pojo, declaredField, resultSet);
            }
        }
        return (T) pojo;
    }

    //通过反射赋值
    private void setValue(Object pojo, Field declaredField, ResultSet resultSet) throws NoSuchMethodException {
        Method setMethod = pojo.getClass().getMethod("set" + firstWordCapital(declaredField.getName()), declaredField.getType());
        setMethod.invoke(pojo, getResult(resultSet, declaredField));

    }
    /**
     * @description: 根据反射判断类型，从resultSet获取对应的参数类型
     * @name: getResult
     * @param: resultSet
     * @param: declaredField
     * @return: java.lang.Object
     * @date: 2020/3/13 8:32
     * @auther: Administrator
    **/

    private Object getResult(ResultSet rs, Field field) throws SQLException {
        Class type = field.getType();
        String dataName = HumpToUnderline(field.getName()); // 驼峰转下划线
        if (Integer.class == type ) {
            return rs.getInt(dataName);
        }else if (String.class == type) {
            return rs.getString(dataName);
        }else if(Long.class == type){
            return rs.getLong(dataName);
        }else if(Boolean.class == type){
            return rs.getBoolean(dataName);
        }else if(Double.class == type){
            return rs.getDouble(dataName);
        }else{
            return rs.getString(dataName);
        }
    }

    /**
     * @description:驼峰转下划线
     * @name: HumpToUnderline
     * @param: name
     * @return: java.lang.String
     * @date: 2020/3/13 8:32
     * @auther: Administrator
    **/

    private String HumpToUnderline(String para) {
        this.getClass();
    }

    private String firstWordCapital(String name) {
        String first = name.substring(0, 1);
        String tail = name.substring(1);
        return first.toUpperCase() + tail;
    }
}
