package com.cdx.test;

import com.cdx.dao.company.CompanyDao;
import com.cdx.domain.company.Company;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

public class batisTest {
//    @Test
//    public void test(){
//// 获取配置文件的输入流
//        InputStream is = batisTest.class.getClassLoader().getResourceAsStream("SqlSessionConfig.xml");
//        // 获取工厂对象
//        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
//        // 获取SqlSession
//        SqlSession sqlSession = sqlSessionFactory.openSession();
//        CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
//        List<Company> companies = companyDao.selectAll();
//        for (Company company : companies) {
//            System.out.println(company  );
//        }
//        sqlSession.close();
//    }
}
