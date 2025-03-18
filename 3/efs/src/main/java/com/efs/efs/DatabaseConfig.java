package com.efs.efs;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DatabaseConfig {

    @Bean
    public DataSource shard1DataSource(){
        return DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:3306/shard1")
                .username("root")
                .password("root")
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .build();
    }

    @Bean
    public DataSource shard2DataSource(){
        return DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:3306/shard2")
                .username("root")
                .password("root")
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .build();
    }

    @Bean
    @Primary
    public DataSource dynamicDataSource() {
        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put("shard1", shard1DataSource());
        dataSourceMap.put("shard2", shard2DataSource());

        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setTargetDataSources(dataSourceMap);
        dynamicDataSource.setDefaultTargetDataSource(shard1DataSource());

        return dynamicDataSource;
    }

}
