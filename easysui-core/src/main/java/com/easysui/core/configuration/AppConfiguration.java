package com.easysui.core.configuration;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author CHAO 2019/5/19 2:16
 */
@Getter
@Setter
@ToString
public class AppConfiguration {
    /**
     * 应用名称
     */
    private String appName;
    /**
     * 节点ID
     */
    private String nodeId;
}
