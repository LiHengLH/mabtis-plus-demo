package com.baomidou.springboot.config;


import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;

@Configuration
public class DataSourceConfig {
	@Bean(name ="datasource1")
    @Primary
    @ConfigurationProperties(prefix ="spring.datasource") // application.properteis中对应属性的前缀
    public DataSource dataSource1() {
        return DataSourceBuilder.create().build();
    }




    @Bean(name = "datasource2")
    @ConfigurationProperties(prefix ="spring.datasource1") // application.properteis中对应属性的前缀
    public DataSource dataSource2() {
        return DataSourceBuilder.create().build();
    }
    
   
    @Bean(name ="datasource")
    
    public DynamicDataSource dynamicDataSource(@Qualifier(value ="datasource1")DataSource dataSource1,@Qualifier(value ="datasource2")DataSource dataSource2){
    DynamicDataSource bean =new DynamicDataSource();
    Map<Object,Object> targetDataSources =new HashMap<> ();
    targetDataSources.put("datasource1",dataSource1);
    targetDataSources.put("datasource2", dataSource2);
    bean.setTargetDataSources(targetDataSources);
    bean.setDefaultTargetDataSource(dataSource1);
    return bean;
    }
    @Bean(name ="sessionFactory1")
    @ConfigurationProperties(prefix ="mybatis-plus1")
    @ConfigurationPropertiesBinding()
    @Primary
    public MybatisSqlSessionFactoryBean sqlSessionFactory1(@Qualifier(value ="datasource")DataSource dataSource){
    MybatisSqlSessionFactoryBean bean =new MybatisSqlSessionFactoryBean();
    bean.setDataSource(dataSource);
    return bean;
    }
    @Bean(name ="sessionFactory2")
    @ConfigurationProperties(prefix ="mybatis-plus2")
    @ConfigurationPropertiesBinding()
    public MybatisSqlSessionFactoryBean sqlSessionFactory2(@Qualifier(value ="datasource")DataSource dataSource){
    MybatisSqlSessionFactoryBean bean =new MybatisSqlSessionFactoryBean();
    bean.setDataSource(dataSource);
    return bean;
    }
}