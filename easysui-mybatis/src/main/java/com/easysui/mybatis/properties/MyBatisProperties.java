package com.easysui.mybatis.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 属性配置
 * mybatis:
 * type-aliases-package: com.ring.api.model
 * mapper-locations : classpath*:mapper/*.xml
 * config-location : classpath:mybatis-config.xml
 * base-package : com.ring.core.mapper
 *
 * @author CHAO
 */
@ConfigurationProperties(prefix = "mybatis")
public class MyBatisProperties {

    private String typeAliasesPackage;
    private String mapperLocations;
    private String configLocation;
    private String basePackage;

    public String getTypeAliasesPackage() {
        return typeAliasesPackage;
    }

    public void setTypeAliasesPackage(String typeAliasesPackage) {
        this.typeAliasesPackage = typeAliasesPackage;
    }

    public String getMapperLocations() {
        return mapperLocations;
    }

    public void setMapperLocations(String mapperLocations) {
        this.mapperLocations = mapperLocations;
    }

    public String getConfigLocation() {
        return configLocation;
    }

    public void setConfigLocation(String configLocation) {
        this.configLocation = configLocation;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

}
