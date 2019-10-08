package com.easysui.mybatis.config;

import com.easysui.core.exception.NecessaryException;
import com.easysui.mybatis.properties.MyBatisProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * @author Chao Shibin
 */
@Slf4j
@Configuration
@ConditionalOnBean(DataSource.class)
@ConditionalOnClass({SqlSessionFactory.class, SqlSessionFactoryBean.class})
public class MyBatisAutoConfiguration {

    private static final String SQL_SESSION_FACTORY = "sqlSessionFactory";
    private static final String TRANSACTION_MANAGER = "transactionManager";

    @Configuration
    @AutoConfigureAfter(DataSourceAutoConfiguration.class)
    @EnableConfigurationProperties(MyBatisProperties.class)
    protected static class SqlSessionFactoryConfiguration {
        @Autowired
        private MyBatisProperties myBatisProperties;
        @Autowired
        private DataSource dataSource;

        @Bean(name = SQL_SESSION_FACTORY)
        public SqlSessionFactory sqlSessionFactory() {
            try {
                SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
                PathMatchingResourcePatternResolver loader = new PathMatchingResourcePatternResolver();
                factory.setMapperLocations(loader.getResources(myBatisProperties.getMapperLocations()));
                factory.setConfigLocation(loader.getResource(myBatisProperties.getConfigLocation()));
                factory.setTypeAliasesPackage(myBatisProperties.getTypeAliasesPackage());
                factory.setDataSource(dataSource);
                return factory.getObject();
            } catch (Exception e) {
                log.error("Could not configure mybatis session factory", e);
                throw new NecessaryException("SqlSessionFactory create failure");
            }
        }

        @Bean(name = TRANSACTION_MANAGER)
        @ConditionalOnMissingBean(PlatformTransactionManager.class)
        public DataSourceTransactionManager transactionManager() {
            return new DataSourceTransactionManager(dataSource);
        }
    }

    @Configuration
    @ConditionalOnClass(MapperScannerConfigurer.class)
    @AutoConfigureAfter(SqlSessionFactoryConfiguration.class)
    protected static class MapperScannerConfigurerConfiguration implements EnvironmentAware {

        private Environment environment;

        @Override
        public void setEnvironment(Environment environment) {
            this.environment = environment;
        }

        @Bean
        public MapperScannerConfigurer mapperScannerConfigurer() {
            MapperScannerConfigurer config = new MapperScannerConfigurer();
            config.setBasePackage(environment.getProperty("easysui.mybatis.base-package"));
            config.setSqlSessionFactoryBeanName(SQL_SESSION_FACTORY);
            return config;
        }
    }

}
