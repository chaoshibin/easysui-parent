package com.easysui.common.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 功能描述:
 * <p/>
 *
 * @author CHAO 新增日期：2018/2/11
 * @author CHAO 修改日期：2018/2/11
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ResultEnum {
    OK("OK", "成功"),
    ERROR("ERROR", "失败"),
    RETRY("RETRY", "重试"),
    DISTRIBUTE_LOCK_SERVICE_INVALID("777776", "分布式锁服务不可用"),
    DISTRIBUTE_LOCK_FAIL("777777", "争夺分布式锁失败"),
    ILLEGAL_PARAM("999998", "参数验证不通过"),
    INTERNAL_SERVER_ERROR("999999", "服务内部错误");

    private String code;
    private String msg;
}
