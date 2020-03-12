package com.leesin.mybatis.v1.mapper;


// @Entity(Blog.class)
public interface BlogMapper {
    /**
     * 根据主键查询文章
     * @param bid
     * @return
     */
    // @Select("select * from blog where bid = %d")
    public Blog selectBlogById(Integer bid);

}
