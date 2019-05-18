package com.easysui.mybatis.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
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
@Getter
@Setter
@ToString
@ConfigurationProperties(prefix = "easysui.mybatis")
public class MyBatisProperties {
    private String typeAliasesPackage;
    private String mapperLocations;
    private String configLocation;
    private String basePackage;
}
