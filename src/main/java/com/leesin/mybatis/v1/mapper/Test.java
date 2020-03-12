package com.leesin.mybatis.v1.mapper;

import com.leesin.mybatis.v1.Configuration;
import com.leesin.mybatis.v1.Executor;
import com.leesin.mybatis.v1.SqlSession;

/**
 * @description:
 * @author: Leesin Dong
 * @date: Created in 2020/3/12 12:33
 * @version:
 * @modified By:
 */
public class Test {
    public static void main(String[] args) {
        SqlSession sqlSession = new SqlSession(new Configuration(),new Executor());
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
        mapper.selectBlogById(1);
    }
}
