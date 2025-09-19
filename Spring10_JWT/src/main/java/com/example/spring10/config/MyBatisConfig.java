package com.example.spring10.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;



@Configuration
public class MyBatisConfig {

  @Bean
  public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
      SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
      factory.setDataSource(dataSource);

      // XML 매퍼들 등록 
      PathMatchingResourcePatternResolver resolver =
              new PathMatchingResourcePatternResolver();
      factory.setMapperLocations(resolver.getResources("classpath:mapper/**/*.xml"));

      // 선택: 타입 별칭
      factory.setTypeAliasesPackage("com.example.spring10.dto");

      return factory.getObject();
  }
}
