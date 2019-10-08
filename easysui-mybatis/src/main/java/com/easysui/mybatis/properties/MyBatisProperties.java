package com.easysui.mybatis.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 属性配置
 * mybatis:
 * type-aliases-package: com.ring.api.model
 * mapper-locations : classpath*:mapper/*.xml
 * config-location : classpath:mybatis-config.xml
 * base-package : com.ring.core.mapper
 *
 * @author Chao Shibin
 */
@Getter
@Setter
@ToString
public class MyBatisProperties {
    private String typeAliasesPackage;
    private String mapperLocations;
    private String configLocation;
    private String basePackage;
}
