package com.leesin.mybatis.v2.cache;

/**
 * @description:缓存key
 * @author: Leesin Dong
 * @date: Created in 2020/3/12 21:22
 * @version:
 * @modified By:
 */
public class Cachekey {
    //默认哈希值
    private static final int DEFAULT_HASHCODE = 17;
    //倍数
    private static final int DEFAULT_MULTIPLIER = 37;

    private int hashCode;
    private int count;
    private int multiplier;

    public Cachekey() {
        this.hashCode = DEFAULT_HASHCODE;
        this.count = 0;
        this.multiplier = DEFAULT_MULTIPLIER;
    }

    /**
     * @description: 返回CacheKey的值
     * @name: getCode
     * @param:
     * @return: int
     * @date: 2020/3/12 21:26
     * @auther: Administrator
    **/
    public int getCode() {
        return hashCode;
    }

    public void update(Object object) {
        int baseHahsCode = object == null ? 1 : object.hashCode();
        count++;
        baseHahsCode *= count;
        hashCode = multiplier * hashCode + baseHahsCode;

    }
}
