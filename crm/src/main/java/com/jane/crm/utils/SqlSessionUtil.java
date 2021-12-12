package com.jane.crm.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

public class SqlSessionUtil {

    private SqlSessionUtil(){}

    private static SqlSessionFactory sqlSessionFactory = null;

    static {
        InputStream inputStream = null;

        try {
            String path = "mybatis-config.xml";
            inputStream = Resources.getResourceAsStream(path);
        }catch (Exception e){
            e.printStackTrace();
        }

        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    private static ThreadLocal<SqlSession> t = new ThreadLocal<SqlSession>();

    public static SqlSession getSession(){

        SqlSession sqlSession = t.get();
        if (sqlSession==null){
            sqlSession = sqlSessionFactory.openSession();
            t.set(sqlSession);
        }
        return sqlSession;
    }

    public static void myClose(SqlSession sqlSession){
        if (sqlSession != null){
            sqlSession.close();
            t.remove();
        }
    }
}
