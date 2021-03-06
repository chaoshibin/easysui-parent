package com.easysui.core.util;

import com.easysui.core.enums.ResultEnum;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Chao Shibin
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final Result SYSTEM_ERROR = Result.of(ResultEnum.INTERNAL_SERVER_ERROR);

    /**
     * 状态码
     */
    private String code;

    /**
     * 消息明细
     */
    private String msg;

    /**
     * 结果对象
     */
    private T value;

    public static <T> Result<T> ok(T value) {
        return Result.of(ResultEnum.OK.getCode(), ResultEnum.OK.getMsg(), value);
    }

    public static <T> Result<T> ok(T value, String msg) {
        return Result.of(ResultEnum.OK.getCode(), msg, value);
    }

    public static <T> Result<T> error(String msg) {
        return Result.of(ResultEnum.ERROR.getCode(), msg, null);
    }

    public static <T> Result<T> error(T value, String msg) {
        return Result.of(ResultEnum.ERROR.getCode(), msg, value);
    }

    public static <T> Result<T> of(String code, String msg, T value) {
        return new Result<>(code, msg, value);
    }

    public static <T> Result<T> of(ResultEnum resultEnum) {
        return Result.of(resultEnum.getCode(), resultEnum.getMsg(), null);
    }

    public boolean isOk() {
        return Objects.equals(ResultEnum.OK.getCode(), this.code);
    }

    public boolean isError() {
        return !isOk();
    }
}
