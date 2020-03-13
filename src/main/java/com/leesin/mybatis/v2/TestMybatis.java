package com.leesin.mybatis.v2;


import com.leesin.mybatis.v2.mapper.Blog;
import com.leesin.mybatis.v2.mapper.BlogMapper;
import com.leesin.mybatis.v2.session.Configuration;
import com.leesin.mybatis.v2.session.DefaultSqlSession;
import com.leesin.mybatis.v2.session.SqlSessionFactory;

public class TestMybatis {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactory();
        DefaultSqlSession defaultSqlSession = sqlSessionFactory.build().openSqlSession();
        BlogMapper mapper = defaultSqlSession.getMapper(BlogMapper.class);
        Blog blog = mapper.selectBlogById(1);

        System.out.println("第一次查询: " + blog);
        System.out.println();
        blog = mapper.selectBlogById(1);
        System.out.println("第二次查询: " + blog);
    }
}
