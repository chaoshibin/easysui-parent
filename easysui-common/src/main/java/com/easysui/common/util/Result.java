package com.easysui.common.util;

import com.easysui.common.enums.ResultEnum;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author CHAO
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;
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

    public static <T> Result ok(T value) {
        return Result.create(ResultEnum.OK.getCode(), ResultEnum.OK.getMsg(), value);
    }

    public static <T> Result ok(String msg, T value) {
        return Result.create(ResultEnum.OK.getCode(), msg, value);
    }

    public static Result error(String msg) {
        return Result.create(ResultEnum.ERROR.getCode(), msg, null);
    }

    public static <T> Result error(String msg, T value) {
        return Result.create(ResultEnum.ERROR.getCode(), msg, value);
    }

    public static Result retry(String msg) {
        return Result.create(ResultEnum.RETRY.getCode(), msg, null);
    }

    public static <T> Result retry(String msg, T value) {
        return Result.create(ResultEnum.RETRY.getCode(), msg, value);
    }

    public static <T> Result<T> create(String code, String msg) {
        return new Result<>(code, msg, null);
    }

    public static <T> Result<T> create(String code, String msg, T value) {
        return new Result<>(code, msg, value);
    }

    public boolean isOk() {
        return Objects.equals(ResultEnum.OK.getCode(), this.code);
    }

    public boolean isError() {
        return Objects.equals(ResultEnum.ERROR.getCode(), this.code);
    }

    public boolean isRetry() {
        return Objects.equals(ResultEnum.RETRY.getCode(), this.code);
    }
}
