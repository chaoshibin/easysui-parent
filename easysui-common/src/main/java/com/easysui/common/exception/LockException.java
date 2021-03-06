package com.easysui.common.exception;

/**
 * 功能描述: RPC异常
 * <p/>
 *
 * @author Chao Shibin 新增日期：2018/2/9
 * @author Chao Shibin 修改日期：2018/2/9
 * @version 1.0.0
 * @since 1.0.0
 */
public final class LockException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public LockException(String message) {
        super(message);
    }
}
