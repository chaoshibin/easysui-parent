package com.easysui.common.exception;


import com.easysui.core.enums.ResultEnum;

/**
 * 功能描述: RPC异常
 * <p/>
 *
 * @author Chao Shibin 新增日期：2018/2/9
 * @author Chao Shibin 修改日期：2018/2/9
 * @version 1.0.0
 * @since 1.0.0
 */
public final class RpcException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private final String code;

    public RpcException(String code, String message) {
        super(message);
        this.code = code;
    }

    public RpcException(ResultEnum result) {
        super(result.getMsg());
        this.code = result.getCode();
    }

    public String getCode() {
        return code;
    }
}
