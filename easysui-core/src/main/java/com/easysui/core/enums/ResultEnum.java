package com.easysui.core.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 功能描述:
 * <p/>
 *
 * @author Chao Shibin 新增日期：2018/2/11
 * @author Chao Shibin 修改日期：2018/2/11
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public enum ResultEnum {
    OK("000000", "成功"),
    ERROR("100001", "失败"),
    ILLEGAL_PARAM("100002", "参数验证不通过"),
    RETRY("999996", "重试"),
    DISTRIBUTE_LOCK_SERVICE_INVALID("999997", "分布式锁服务不可用"),
    DISTRIBUTE_LOCK_FAIL("999998", "争夺分布式锁失败"),
    INTERNAL_SERVER_ERROR("999999", "服务内部错误");

    private String code;
    private String msg;
}
