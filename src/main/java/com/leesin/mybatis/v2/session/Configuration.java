package com.leesin.mybatis.v2.session;

import com.leesin.mybatis.v1.Executor;
import com.leesin.mybatis.v2.Executor.CachingExecutor;
import com.leesin.mybatis.v2.Executor.SimpleExecutor;
import com.leesin.mybatis.v2.TestMybatis;
import com.leesin.mybatis.v2.annotation.Entity;
import com.leesin.mybatis.v2.annotation.Select;
import com.leesin.mybatis.v2.bingding.MapperRegistry;
import com.leesin.mybatis.v2.plugin.InterceptorChain;
import org.omg.PortableInterceptor.Interceptor;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @description:
 * @author: Leesin Dong
 * @date: Created in 2020/3/12 17:42
 * @version:
 * @modified By:
 */
public class Configuration {
    //sql映射关系配置，使用注解是不用重复配置
    public static final ResourceBundle sqlMappings;
    //全局配置
    public static final ResourceBundle properties;
    //维护接口与工厂类关系
    public static final MapperRegistry MAPPER_REGISTRY = new MapperRegistry();
    //维护接口方法与sql关系
    public static final Map<String, String> mappedStatements = new HashMap<>();
    //插件
    private InterceptorChain interceptorChain = new InterceptorChain();
    //所有mapper接口
    private List<Class<?>> mapperList = new ArratList<>();
    //类所有文件
    private List<String> classPaths = new ArrayList<String>();

    static {
        sqlMappings = ResourceBundle.getBundle("sql");
        properties = ResourceBundle.getBundle("mybatis");
    }

    /**
     * @description: 初始化时解析全局配置文件
     * @name: Configuration
     * @param:
     * @return:
     * @date: 2020/3/12 17:48
     * @auther: Administrator
     **/
    public Configuration() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        //    note：在properties和注解中重复配置sql会覆盖
        //    1.解析sql.properties
        for (String key : sqlMappings.keySet()) {
            Class mapper = null;
            String statement = null;
            String pojoStr = null;
            Class pojo = null;
            //properties中的value用--隔开，第一个是sql语句
            statement = sqlMappings.getString(key).split("--")[0];
            // properties中的value用--隔开，第二个是pojo类型
            pojoStr = sqlMappings.getString(key).split("--")[1];
            //    properties中的key是接口类型+方法
            //    从接口类型+方法中截取接口类型
            try {
                mapper = Class.forName(key.substring(0, key.lastIndexOf(".")));
                pojo = Class.forName(pojoStr);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            //接口与返回的实体类关系
            MAPPER_REGISTRY.addMapper(mapper, pojo);
            //接口方法与sql关系
            mappedStatements.put(key, statement);
        }
        //2.解析mapper接口配置，扫描注册
        String mapperPath = properties.getString("mapper.path");
        scanPackage(mapperPath);
        for (Class<?> aClass : mapperList) {
            parsingClass(aClass);
        }
        String pluginPathValue = properties.getString("plugin.path");
        String[] pluginPaths = pluginPathValue.split(",");
        if (pluginPaths != null) {
            //    将插件添加到InterceptorChain中
            for (String pluginPath : pluginPaths) {
                Interceptor interceptor = null;
                interceptor = (Interceptor) Class.forName(pluginPath).newInstance();
                interceptorChain.addInterceptor(interceptor);
            }
        }
    }

    /**
     * @description: 根据statement判断是否存在映射的sql
     * @name: hasStatement
     * @param: statementName
     * @return: boolean
     * @date: 2020/3/12 19:12
     * @auther: Administrator
     **/
    public boolean hasStatement(String statementName) {
        return mappedStatements.containsKey(statementName);
    }

    /**
     * @description: 根据statement ID获取sql
     * @name: getMappedStatement
     * @param: id
     * @return: java.lang.String
     * @date: 2020/3/12 19:14
     * @auther: Administrator
     **/

    public String getMappedStatement(String id,String a) {
        return mappedStatements.get(id);
    }

    public <T> T getMapper(Class<T> clazz, DefaultSqlSession sqlSession) {
        return MAPPER_REGISTRY.getMapper(clazz, sqlSession);
    }

    /**
     * @description: 创建执行器
     * @name: newExecutor
     * @param:
     * @return: com.leesin.mybatis.v1.Executor
     * @date: 2020/3/12 20:01
     * @auther: Administrator
     **/
    public Executor newExecutor() {
        Executor executor = null;
        if (properties.getString("cached.enabled").equals("true")) {
            executor = new CachingExecutor(new SimpileExecutor);
        } else {
            new SimpleExecutor();
        }
        //目前只拦截了Executor，所有的插件都对executor进行代理，没有对拦截类和方法签名进行判断。
        if (interceptorChain.hasPlugin()) {
            return (Executor) interceptorChain.pluginAll(executor);
        }
        return executor;
    }

    /**
     * @description: 解析mapper接口上配置的注解（sql语句）
     * @name: parsingClass
     * @param: aClass
     * @return: void
     * @date: 2020/3/12 21:38
     * @auther: Administrator
     **/
    private void parsingClass(Class<?> mapper) {
        //    解析类上的注解
        //    如果有@Entity注解，说明是查询数据库的接口
        if (mapper.isAnnotationPresent(Entity.class)) {
            for (Annotation annotation : mapper.getAnnotations()) {
                if (annotation.annotationType().equals(Entity.class)) {
                    //注册接口与实体类的映射关系
                    MAPPER_REGISTRY.addMapper(mapper, ((Entity) annotation).value());
                }

            }
        }
        //解析方法上的注解
        Method[] methods = mapper.getMethods();
        for (Method method : methods) {
            // 解析@Select注解的SQL语句
            if (method.isAnnotationPresent(Select.class)) {
                for (Annotation declaredAnnotation : method.getDeclaredAnnotations()) {
                    if (declaredAnnotation.annotationType().equals(Select.class)) {
                        // 注册接口类型+方法名和SQL语句的映射关系
                        String statement = method.getDeclaringClass().getName() + "." + method.getName();
                        mappedStatements.put(statement, ((Select) declaredAnnotation).value());
                    }
                }
            }
        }
    }

    /**
     * @description: 根据配置文件的Mapper接口枯井，扫描所有接口
     * @name: scanPackage
     * @param: mapperPath
     * @return: void
     * @date: 2020/3/12 21:49
     * @auther: Administrator
    **/
    private void scanPackage(String mapperPath) {
        String classPath = TestMybatis.class.getResource("/").getPath();
        mapperPath = mapperPath.replace(".", "/");
        String mainPath = classPath + mapperPath;
        doPath(new File(mainPath));
        for (String className : classPaths) {
            className = className.replace(classPath.replace("/","\\").replaceFirst("\\\\",""),"").replace("\\",".").replace(".class","");
            Class<?> clazz = null;
            try {
                clazz = Class.forName(className);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if(clazz.isInterface()){
                mapperList.add(clazz);
            }
        }
    }

    /**
     * @description: 获取文件或文件夹下所有的类
     * @name: doPath  
     * @param: file
     * @return: void
     * @date: 2020/3/12 22:24
     * @auther: Administrator
    **/
    private void doPath(File file) {
        if (file.isDirectory()) {
            for (File listFile : file.listFiles()) {
                doPath(listFile);
            }
        } else {
            if (file.getName().endsWith(".class")) {
                classPaths.add(file.getPath());
            }
        }
    }
}
