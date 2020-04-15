package com.cennavi.tp.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Created by sunpengyan on 2018/12/13.
 * 多数据源配置类
 */
@Configuration
public class DataSourceConfig {

    @Bean(name = "dataSource")
    @Qualifier("dataSource")
    @Primary
    @ConfigurationProperties(prefix="spring.datasource")
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "pgxlDataSource")
    @Qualifier("pgxlDataSource")
    @ConfigurationProperties(prefix="pgxl.spring.datasource")
    public DataSource secondaryDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "jdbcTemplate")
    public JdbcTemplate primaryJdbcTemplate(@Qualifier("dataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = "pgxlJdbcTemplate")
    public JdbcTemplate secondaryJdbcTemplate(@Qualifier("pgxlDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
