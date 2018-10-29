//package com.guiji.dispatch.util;
//
//import org.apache.ibatis.io.Resources;
//import org.apache.ibatis.session.SqlSession;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.apache.ibatis.session.SqlSessionFactoryBuilder;
//
//import java.io.IOException;
//import java.io.Reader;
//
///*
// * 工具类
// * */
//public class MybatisUtil {
//    private static ThreadLocal<SqlSession> threadLocal = new ThreadLocal<SqlSession>();
//    private static SqlSessionFactory sqlSessionFactory;
//
//    /*
//     * 加载位于src/mybatis.xml配置文件
//     * */
//    static {
//        try {
//            Reader reader = Resources.getResourceAsReader("mappers/mybatis.xml");
//            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//    }
//
//    //禁止外部访问
//    private MybatisUtil() {
//    }
//
//    /*
//     * 获取SqlSession
//     * */
//    public static SqlSession getSqlSession() {
//        //从当前线程中获取sqlSession对象
//        SqlSession sqlSession = threadLocal.get();
//        if (sqlSession == null) {
//            //如果sqlSessionFactory对象不为空情况下，那么获取SqlSession对象。
//            sqlSession = sqlSessionFactory.openSession();
//            //将sqlSession与当前线程绑定在一起
//            threadLocal.set(sqlSession);
//        }
//        //直接返回sqlSession对象
//        return sqlSession;
//    }
//
//    /*
//     * 关闭SqlSession与当前线程分离
//     * */
//    public static void closeSqlSession() {
//        //从当前线程中获取sqlSession对象
//        SqlSession sqlSession = threadLocal.get();
//        if (sqlSession != null) {
//            //关闭sqlSession对象
//            sqlSession.close();
//            //分开当前线程与sqlSession对象的关系，目的是让GC尽早回收
//            threadLocal.remove();
//        }
//    }
//}